package br.com.sismico.kafkastreamsexample.config

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
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
    fun producerFactory(): Producer<String, SpecificRecord?> =
        KafkaProducer(producerConfigProps())

    @Bean
    fun consumerFactory(): ConsumerFactory<String, SpecificRecord?> =
        DefaultKafkaConsumerFactory(consumerConfigProps())

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, SpecificRecord?>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SpecificRecord?>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    private fun producerConfigProps(): MutableMap<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        return configProps
    }

    private fun consumerConfigProps(): MutableMap<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryAddress
        configProps[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        return configProps
    }
}