package br.com.sismico.kafkastreamsexample.producer

import br.com.sismico.kafkastreamsexample.entity.Address
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AddressProducer(
    val producer: Producer<String, Any?>
) {

    val log: Logger = LoggerFactory.getLogger(AddressProducer::class.java)

    fun send(username: String, address: String) {
        log.info("Address $address sent for user $username")
        producer.send(ProducerRecord( ProducerTopics.ADDRESS.topic, Address(username, address))).get()
    }
}