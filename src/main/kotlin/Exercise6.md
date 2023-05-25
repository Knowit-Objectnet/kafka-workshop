# Exercise 6: Create a review system for 

This exercise is intended to complete in the ksqlDB CLI. Open it by running the following command in a shell

    docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088

We want to be able to let users review products. Reviews should be in a stream and 
create a table that shows the average review score and total number of reviews per product.

Inserting data into the stream should update the table accordingly.

Duration: ~10 minutes
