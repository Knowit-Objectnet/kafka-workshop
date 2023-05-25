import io.confluent.kafka.streams.serdes.avro.PrimitiveAvroSerde
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import no.knowit.kafkaworkshop.avro.UserSignup
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Kafka Streams
 * Exercise 3: Filter user signups by country. The topic user_signups contains all signups for the store.
 * We want to separate each country into its own stream, and output it to its separate topic.
 *
 * The end goal should be to have three topics: user_signups_norway, user_signups_sweden and user_signups_denmark. Only containing signups for each country.
 *
 * Duration: ~10 minutes
 */
fun main() {
    val logger: Logger = LoggerFactory.getLogger("main")

    val longSerde = PrimitiveAvroSerde<Long>()
    longSerde.configure(schemaRegistryConfig(), true)
    val userSignupSerde = SpecificAvroSerde<UserSignup>()
    userSignupSerde.configure(schemaRegistryConfig(), false)

    val topology = StreamsBuilder().apply {
        stream("user_signups", Consumed.with(longSerde, userSignupSerde))
            .filter { _, userSignup -> userSignup.country == "Norway" }
            .to("user_signups_norway")
        stream("user_signups", Consumed.with(longSerde, userSignupSerde))
            .filter { _, userSignup -> userSignup.country == "Sweden" }
            .to("user_signups_sweden")
        stream("user_signups", Consumed.with(longSerde, userSignupSerde))
            .filter { _, userSignup -> userSignup.country == "Denmark" }
            .to("user_signups_denmark", Produced.with(longSerde, userSignupSerde))
    }.build()

    val streams = KafkaStreams(topology, streamsConfig("exercise-3")).apply {
        cleanUp()
        start()
    }

    Runtime.getRuntime().addShutdownHook(Thread { streams.close() })
}