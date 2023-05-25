# Exercise 5: Revenue per product

This exercise is intended to complete in the ksqlDB CLI. Open it by running the following command in a shell

    docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088

Create summarized data from the purchased products topic on kafka. 
The summarized data should be stored in a new table called revenue_per_product. 
The table should have columns for productid, currency and revenue.

Be careful to not mix the different currencies.

Duration: ~10 minutes

# Solution 5

Remembering to set the offset to earliest.

    SET 'auto.offset.reset'='earliest';

Creating a new stream from an existing Kafka topic. Stream gets the schema already stored in Schema Registry.

    CREATE STREAM purchased_products WITH (KAFKA_TOPIC='purchased_products', KEY_FORMAT='AVRO', VALUE_FORMAT='AVRO');

Creating a new table to hold the summarized data. Table has columns for productid, currency and revenue.

    CREATE TABLE revenue_per_product AS
        SELECT PRODUCTID, CURRENCY, SUM(PRICE) AS REVENUE
        FROM purchased_products
        GROUP BY PRODUCTID, CURRENCY;


Querying the table shows the expected results.

    SELECT * FROM revenue_per_product;