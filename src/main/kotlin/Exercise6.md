# Exercise 6: Create a review system for 

This exercise is intended to complete in the ksqlDB CLI. Open it by running the following command in a shell

    docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088

We want to be able to let users review products. Reviews should be in a stream and 
create a table that shows the average review score and total number of reviews per product.

Inserting data into the stream should update the table accordingly.

Duration: ~10 minutes

# Solution 6

Remembering to set the offset to earliest.

    SET 'auto.offset.reset'='earliest';

Creating a new stream to store reviews. Stream has columns for review_id, user_id, product_id and score.


    CREATE STREAM reviews (REVIEW_ID BIGINT KEY, USER_ID BIGINT, PRODUCT_ID BIGINT, SCORE INT) 
        WITH (KAFKA_TOPIC='reviews', KEY_FORMAT='AVRO', VALUE_FORMAT='AVRO', PARTITIONS=1, REPLICAS=1);

Describing the stream yields our expected result.

    DESCRIBE reviews;

Creating a new table to hold the average review score and total number of reviews per product.

    CREATE TABLE review_stats AS
        SELECT PRODUCT_ID, AVG(SCORE) AS AVERAGE_SCORE, COUNT(*) AS TOTAL_REVIEWS
        FROM reviews
        GROUP BY PRODUCT_ID;

Insert new data to verify the table is working as expected.

    INSERT INTO reviews (REVIEW_ID, USER_ID, PRODUCT_ID, SCORE) VALUES (1, 1, 1, 5);
    INSERT INTO reviews (REVIEW_ID, USER_ID, PRODUCT_ID, SCORE) VALUES (2, 2, 1, 1);
    INSERT INTO reviews (REVIEW_ID, USER_ID, PRODUCT_ID, SCORE) VALUES (3, 3, 2, 2);
    INSERT INTO reviews (REVIEW_ID, USER_ID, PRODUCT_ID, SCORE) VALUES (4, 4, 2, 2);

Querying the table shows the expected results.

    SELECT * FROM review_stats;
