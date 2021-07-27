package br.com.sismico.kafkastreamsexample.stream

import br.com.sismico.kafkastreamsexample.producer.ClientProducer
import br.com.sismico.kafkastreamsexample.producer.ProducerTopics
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*

@Configuration
class StreamExample {
    val log: Logger = LoggerFactory.getLogger(StreamExample::class.java)

    @Bean
    fun build(): Topology {
        val builder = StreamsBuilder()
        val clients: KStream<String, String> = builder.stream(ProducerTopics.CLIENT.topic)
        val counter: KTable<String, Long> = clients.groupBy { _, value -> value }
            .count()
        counter.toStream().foreach { key, value -> log.info("User: $key - Count: $value") }
        return builder.build()
    }

}