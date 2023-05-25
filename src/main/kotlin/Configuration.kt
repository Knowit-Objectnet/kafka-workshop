import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import io.confluent.kafka.streams.serdes.avro.PrimitiveAvroSerde
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.streams.StreamsConfig
import java.net.InetAddress
import java.util.*


private fun sharedConfiguration(): Properties {
    val config = Properties()
    config["bootstrap.servers"] = "localhost:9092"
    return config
}

fun consumerConfig(consumerGroupId: String): Properties {
    val consumerConfig = sharedConfiguration()
    consumerConfig[ConsumerConfig.GROUP_ID_CONFIG] = consumerGroupId
    consumerConfig[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = "io.confluent.kafka.serializers.KafkaAvroDeserializer"
    consumerConfig[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = "io.confluent.kafka.serializers.KafkaAvroDeserializer"
    consumerConfig[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
    consumerConfig[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
    consumerConfig[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"

    return consumerConfig
}

fun producerConfig(): Properties {
    val producerConfig = sharedConfiguration()
    producerConfig[ProducerConfig.CLIENT_ID_CONFIG] = InetAddress.getLocalHost().hostName
    producerConfig[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "io.confluent.kafka.serializers.KafkaAvroSerializer"
    producerConfig[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "io.confluent.kafka.serializers.KafkaAvroSerializer"
    producerConfig[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = "http://localhost:8081"
    producerConfig[ProducerConfig.ACKS_CONFIG] = "all"

    return producerConfig
}

fun streamsConfig(name: String): Properties {
    val streamsConfiguration = sharedConfiguration()
    streamsConfiguration[StreamsConfig.APPLICATION_ID_CONFIG] = "$name"
    streamsConfiguration[StreamsConfig.CLIENT_ID_CONFIG] = "$name-client"
    streamsConfiguration[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = PrimitiveAvroSerde::class.java
    streamsConfiguration[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = SpecificAvroSerde::class.java
    streamsConfiguration[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
    streamsConfiguration[StreamsConfig.COMMIT_INTERVAL_MS_CONFIG] = 5 * 1000

    return streamsConfiguration
}

fun schemaRegistryConfig(): Map<String, *> {
    return mapOf(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081")
}
