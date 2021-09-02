package br.com.sismico.kafkastreamsexample.config

import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory


@Configuration
class KafkaConfiguration {
    @Value("\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String? = null
    @Value("\${kafka.schemaRegistry}")
    private val schemaRegistryAddress: String? = null
    @Value("\${kafka.groupId}")
    private val groupId: String? = null

    @Bean
    fun producerFactory(): Producer<String, GenericRecord?> {
        return KafkaProducer(defaultConfigProps())
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, GenericRecord?> {
        val properties = defaultConfigProps()
        properties[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        return DefaultKafkaConsumerFactory(properties)
    }

    private fun defaultConfigProps(): MutableMap<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        return configProps
    }
}