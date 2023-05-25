import no.knowit.kafkaworkshop.avro.Product
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

/**
 * Basic Kafka Producer and Consumer
 * Exercise 1: Add a new currency to the products topic
 *
 * In this exercise we will add a new currency to the products in products topic.
 * We will utilize the KafkaProducer and KafkaConsumer to read and write to the topic.
 * Currency is represented as a map of currency code to price in that currency.
 *
 * Update the price of all products to also include the price in EUR.
 * The price in EUR should be 10% of the price in NOK.
 *
 * Take a look at the schema and data in http://localhost:8080/ to get a better understanding of the data.
 *
 * Duration: ~10 minutes
 */
fun main() {
    val logger: Logger = LoggerFactory.getLogger("main")

    val kafkaConsumer = KafkaConsumer<Long, Product>(consumerConfig("exercise-1"))
    val kafkaProducer = KafkaProducer<Long, Product>(producerConfig())

    kafkaConsumer.subscribe(listOf("products"))

    kafkaConsumer.use { kc ->
        while (true) {
            val records = kc.poll(Duration.ofMillis(500))

            records.forEach { record ->
                val product = record.value()

                logger.info("Received record")

                if(!product.prices.containsKey("EUR")) {
                    product.prices["EUR"] = (product.prices["NOK"]!! * 0.1).toLong()

                    kafkaProducer.send(ProducerRecord(record.topic(), record.key(), product))
                    logger.info("Updated price for product ${product.id} to ${product.prices["EUR"]}")
                }
            }
        }
    }

}