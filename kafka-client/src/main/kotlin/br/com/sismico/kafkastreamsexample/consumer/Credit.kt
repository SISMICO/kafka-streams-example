package br.com.sismico.kafkastreamsexample.consumer

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import br.com.sismico.kafkastreamsexample.producer.CreditProducer
import com.sismico.kafkastreamsexample.CustomerCreated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class Credit(
    val producer: CreditProducer
) {
    val log: Logger = LoggerFactory.getLogger(Credit::class.java)

    @KafkaListener(topics = [CUSTOMER_CREATED])
    fun receive(customer: CustomerCreated) {
        producer.send(customer.getDocument(), calculateScore(customer.getDocument()))
    }

    private fun calculateScore(document: String) =
        document.substring(0, 3).toFloat()
}