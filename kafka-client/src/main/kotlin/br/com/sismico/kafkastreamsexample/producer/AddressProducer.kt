package br.com.sismico.kafkastreamsexample.producer

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AddressProducer(
    val producer: Producer<String, GenericRecord?>
) {

    val log: Logger = LoggerFactory.getLogger(AddressProducer::class.java)

    fun send(username: String, address: String) {
        val addressObject = com.sismico.kafka.Address(username, address)
        log.info("Address ${addressObject.getAddress()} sent for user ${addressObject.getUsername()}")
        producer.send(ProducerRecord( ProducerTopics.ADDRESS.topic, addressObject)).get()
    }
}