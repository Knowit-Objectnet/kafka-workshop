# Exercise 7: Order summary for all purchased products

Our purchased products refers to a order id. We want to create a table describing the order. 
The table should have columns for order ID, number of products purchased, total price of all products and currency.


Duration: ~10 minutes

# Exercise 7: Order summary for all purchased products

Our purchased products refers to a order id. We want to create a table describing the order.
The table should have columns for order ID, number of products purchased, total price of all products and currency.


Duration: ~10 minutes

# Solution 7

Remembering to set the offset to earliest.

    SET 'auto.offset.reset'='earliest';

Creating a new stream from product purchases.


    CREATE STREAM purchased_products WITH (KAFKA_TOPIC='purchased_products', KEY_FORMAT='AVRO', VALUE_FORMAT='AVRO');

Describing the stream yields our expected result.

    DESCRIBE purchased_products;

Creating a new table to hold the average review score and total number of reviews per product.

    CREATE TABLE ORDERS WITH (KAFKA_TOPIC='orders') AS 
        SELECT 
            ORDERID, 
            COUNT(PRICE) AS NUM_OF_PRODUCTS, 
            SUM(PRICE) AS TOTAL_PRICE, 
            CURRENCY FROM purchased_products 
        GROUP BY ORDERID, CURRENCY;


Querying the table shows the expected results.

    SELECT * FROM orders;