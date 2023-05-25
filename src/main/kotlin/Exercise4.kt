import io.confluent.kafka.streams.serdes.avro.PrimitiveAvroSerde
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import no.knowit.kafkaworkshop.avro.PurchasedProduct
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Kafka Streams
 * Exercise 4: Aggregate the total revenue per currency for the store. The output topic is expected to have
 * a key which is of type string and containing the currency. The value is expected to contain the accumulated revenue for that currency.
 *
 * Use the Kafka Streams DSL to accumulate all revenue.
 *
 * Duration: ~15 minutes
 */
fun main() {
    val logger: Logger = LoggerFactory.getLogger("main")

    val longSerdeKey = PrimitiveAvroSerde<Long>()
    longSerdeKey.configure(schemaRegistryConfig(), true)
    val longSerdeValue = PrimitiveAvroSerde<Long>()
    longSerdeValue.configure(schemaRegistryConfig(), false)
    val stringSerde = PrimitiveAvroSerde<String>()
    stringSerde.configure(schemaRegistryConfig(), true)
    val purchasedProductSerde = SpecificAvroSerde<PurchasedProduct>()
    purchasedProductSerde.configure(schemaRegistryConfig(), false)

    val topology = StreamsBuilder().apply {
        stream("purchased_products", Consumed.with(longSerdeKey, purchasedProductSerde))
            .map { k, purchasedProduct -> KeyValue(purchasedProduct.currency, purchasedProduct.price) }
            .groupByKey(Grouped.with(stringSerde, longSerdeValue))
            .aggregate({ 0L }, { _, price, total -> total + price }, Materialized.with(stringSerde, longSerdeValue))
            .toStream()
            .to("total_purchased_products", Produced.with(stringSerde, longSerdeValue))

    }.build()

    val streams = KafkaStreams(topology, streamsConfig("exercise-4")).apply {
        cleanUp()
        start()
    }

    Runtime.getRuntime().addShutdownHook(Thread { streams.close() })
}