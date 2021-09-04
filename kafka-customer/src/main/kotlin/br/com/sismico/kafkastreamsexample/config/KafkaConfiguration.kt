package br.com.sismico.kafkastreamsexample.config

import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class KafkaConfiguration {
    @Value("\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String? = null

    @Value("\${kafka.schemaRegistry}")
    private val schemaRegistryAddress: String? = null

    @Bean
    fun producerFactory(): Producer<String, SpecificRecord?> =
        KafkaProducer(producerConfigProps())

    private fun producerConfigProps() =
        HashMap<String, Any?>()
            .apply {
                this[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryAddress
                this[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
                this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
                this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
            }
}