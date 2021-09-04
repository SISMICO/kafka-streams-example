# About the repository
This project was created to be used as a PoC (Proof of Concept) to group events sent within a time window.

# Repository Structure

## Client project
Kotlin Project that subscribes all topics and presents all information to understand the flow of the events.

## Customer project
Kotlin project with an API to receive a new customer and publish the Customer Created event.

## Credit project
Kotlin project that subscribe Customer Created, calculates its score and publish the Score Calculated event.

## Segment project
Kotlin project that subscribes to some topics to apply Kafka Streams time window, segment the customer and publish the Customer Segmented event.


# How to use
1. Start all containers using Docker Compose:
    ``` bash
    docker-compose up
    ```

1. In a new terminal, build and start Client project
    ``` bash
    cd kafka-client && ./gradlew build
    java -jar build/libs/kafka-client-0.0.1-SNAPSHOT.jar
    ```

1. In a new terminal, build and start Customer project
    ``` bash
    cd kafka-customer && ./gradlew build
    java -jar build/libs/kafka-customer-0.0.1-SNAPSHOT.jar
    ```

1. In a new terminal, build and start Credit project
    ``` bash
    cd kafka-credit && ./gradlew build
    java -jar build/libs/kafka-credit-0.0.1-SNAPSHOT.jar
    ```

1. In a new terminal, build and start Segment project
    ``` bash
    cd kafka-segment && ./gradlew build
    java -jar build/libs/kafka-segment-0.0.1-SNAPSHOT.jar
    ```

1. Call Client API with a document that start with first 3 digits below 800
    ``` bash
    curl --location --request POST 'http://localhost:8091/user?username=Common&fullname=Some%20Common%20Client&document=59081024663'
    ```

1. Call Client API with a document that start with first 3 digits above 800
    ``` bash
    curl --location --request POST 'http://localhost:8091/user?username=Prime&fullname=Some%20Prime%20Customer&document=98872620562'
    ```
