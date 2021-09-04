package br.com.sismico.kafkastreamsexample.consumer

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import br.com.sismico.kafkastreamsexample.config.CUSTOMER_SEGMENTED
import br.com.sismico.kafkastreamsexample.config.SCORE_CALCULATED
import com.sismico.kafkastreamsexample.CustomerCreated
import com.sismico.kafkastreamsexample.CustomerSegmented
import com.sismico.kafkastreamsexample.ScoreCalculated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class Consumers {
    val log: Logger = LoggerFactory.getLogger(Consumers::class.java)

//    @KafkaListener(topics = [CUSTOMER_CREATED])
//    fun customerCreated(customer: CustomerCreated) {
//        log.info("[RECEIVED] Event Received: Customer Created: ${customer.getUsername()} / ${customer.getDocument()}")
//    }

    @KafkaListener(topics = [SCORE_CALCULATED])
    fun scoreCalculated(score: ScoreCalculated) {
        log.info("[RECEIVED] Event Received: Score Calculated: ${score.getDocument()}: ${score.getScore()}")
    }

    @KafkaListener(topics = [CUSTOMER_SEGMENTED])
    fun customerSegmented(segmentation: CustomerSegmented) {
        log.info("[RECEIVED] Event Received: Customer Segmented: ${segmentation.getUsername()}: ${segmentation.getSegment()}")
    }

}