import org.slf4j.Logger
import org.slf4j.LoggerFactory

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

}