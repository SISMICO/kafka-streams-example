package br.com.sismico.kafkastreamsexample.config

import io.confluent.kafka.serializers.KafkaJsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class KafkaProducerConfiguration {
    @Value("\${kafka.bootstrapAddress:}")
    private val bootstrapAddress: String? = null

    @Bean
    fun producerFactory(): Producer<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return KafkaProducer(configProps)
    }
}