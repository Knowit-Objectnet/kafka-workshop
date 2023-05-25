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


}