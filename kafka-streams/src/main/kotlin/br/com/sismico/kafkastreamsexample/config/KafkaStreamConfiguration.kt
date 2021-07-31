package br.com.sismico.kafkastreamsexample.config

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
    @Value("\${kafka.bootstrapAddress:}")
    private val bootstrapAddress: String? = null

    @Bean
    fun streamFactory(): KafkaStreams {
        val configProps = Properties()
        configProps[StreamsConfig.APPLICATION_ID_CONFIG] = "stream-example"
        configProps[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String()::class.java
        configProps[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String()::class.java
        return KafkaStreams(stream, configProps)
    }
}