package br.com.sismico.kafkastreamsexample.producer

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import com.sismico.kafkastreamsexample.CustomerCreated
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerProducer(
    val producer: Producer<String, SpecificRecord?>
) {
    val log: Logger = LoggerFactory.getLogger(CustomerProducer::class.java)

    fun send(username: String, fullname: String, document: String) {
        val client = CustomerCreated(username, fullname, document)
        log.info("[PRODUCER] User created with username: ${client.getUsername()}, name: ${client.getFullname()} and document: ${client.getDocument()}")
        producer.send(ProducerRecord(CUSTOMER_CREATED, client)).get()
    }
}