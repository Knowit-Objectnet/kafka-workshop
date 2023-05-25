import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Basic Kafka Producer and Consumer
 * Exercise 2: Create a new schema which is called ExtendedProduct which is based on Product with an additional field called full name.
 *
 * Create a new topic called extended_products and produce ExtendedProduct to this topic. Fill in the full name field with a formatted name which includes
 * the brand, name, finish and the storage of the product.
 *
 * Topics are automatically created when you produce data, but to ensure the correct settings adding them to the init script in docker-compose.yml is recommended.
 *
 * Existing Avro schemas are located in src/main/avro. You can use the Gradle task 'generateAvroJava' to generate Java classes from the schemas.
 *
 * Duration: ~10 minutes
 */
fun main() {
    val logger: Logger = LoggerFactory.getLogger("main")


}