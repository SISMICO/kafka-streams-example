package br.com.sismico.kafkastreamsexample.producer

import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientProducer(
    val producer: Producer<String, String>
) {

    val log: Logger = LoggerFactory.getLogger(ClientProducer::class.java)

    fun send(username: String) {
        log.info("User sent: $username")
        producer.send(ProducerRecord(ProducerTopics.CLIENT.topic, username)).get()
    }
}