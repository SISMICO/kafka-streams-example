package br.com.sismico.kafkastreamsexample.config

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KafkaStreamConfiguration(
    val stream: Topology
) {
    @Value("\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String? = null

    @Value("\${kafka.schemaRegistry}")
    private val schemaRegistryAddress: String? = null

    @Bean
    fun streamFactory() = KafkaStreams(stream, configurations())

    private fun configurations() =
        Properties()
            .apply {
                this[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryAddress
                this[StreamsConfig.APPLICATION_ID_CONFIG] = "kafka-segment"
                this[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
                this[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String()::class.java
                this[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = SpecificAvroSerde::class.java
            }
}