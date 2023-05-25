# Exercise 5: Revenue per product

This exercise is intended to complete in the ksqlDB CLI. Open it by running the following command in a shell

    docker-compose exec ksqldb-cli ksql http://ksqldb-server:8088

Create summarized data from the purchased products topic on kafka. 
The summarized data should be stored in a new table called revenue_per_product. 
The table should have columns for productid, currency and revenue.

Be careful to not mix the different currencies.

Duration: ~10 minutes
