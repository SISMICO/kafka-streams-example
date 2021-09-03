package br.com.sismico.kafkastreamsexample.producer

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import br.com.sismico.kafkastreamsexample.config.SCORE_CALCULATED
import com.sismico.kafkastreamsexample.CustomerCreated
import com.sismico.kafkastreamsexample.ScoreCalculated
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreditProducer(
    val producer: Producer<String, SpecificRecord?>
) {
    val log: Logger = LoggerFactory.getLogger(CreditProducer::class.java)

    fun send(document: String, score: Float) {
        val event = ScoreCalculated(document, score)
        log.info("[PRODUCER] Score Calculated for document: ${event.getDocument()} with score: ${event.getScore()}")
        producer.send(ProducerRecord(SCORE_CALCULATED, event)).get()
    }
}