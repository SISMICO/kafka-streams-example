package br.com.sismico.kafkastreamsexample.consumer

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import com.sismico.kafkastreamsexample.CustomerCreated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class Consumers {
    val log: Logger = LoggerFactory.getLogger(Consumers::class.java)

    @KafkaListener(topics = [CUSTOMER_CREATED], groupId = "consumer-id")
    fun customer(customer: CustomerCreated) {
        log.info("Event Received: Customer Created: ${customer.getUsername()}")
    }

}