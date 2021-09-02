package br.com.sismico.kafkastreamsexample.producer

import br.com.sismico.kafkastreamsexample.entity.Client
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientProducer(
    val producer: Producer<String, GenericRecord?>
) {

    val log: Logger = LoggerFactory.getLogger(ClientProducer::class.java)

    fun send(username: String) {
        val client = com.sismico.kafka.Client(username)
        log.info("User sent: ${client.getUsername()}")
        producer.send(ProducerRecord(ProducerTopics.CLIENT.topic, client)).get()
    }
}