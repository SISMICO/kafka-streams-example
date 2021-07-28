package br.com.sismico.kafkastreamsexample.stream

import br.com.sismico.kafkastreamsexample.producer.ProducerTopics
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.JoinWindows
import org.apache.kafka.streams.kstream.KStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class StreamExample {
    val log: Logger = LoggerFactory.getLogger(StreamExample::class.java)

    @Bean
    fun build(): Topology {
        val builder = StreamsBuilder()

        val clients: KStream<String, String> = builder.stream(ProducerTopics.CLIENT.topic)
        val address: KStream<String, String> = builder.stream(ProducerTopics.ADDRESS.topic)

//        val counter: KTable<String, Long> = clients.groupBy { _, value -> value }
//            .count()
//        counter.toStream().foreach { key, value -> log.info("User: $key - Count: $value") }

        val kaddress = address
            .selectKey { _, value -> value.split(":")[0] }
            .mapValues { map -> map.split(":")[1] }
        val kclient = clients.selectKey { _, value -> value }

        kclient.join(
            kaddress,
            { left: String, right: String -> "$left/$right" },
            JoinWindows.of(Duration.ofSeconds(10L))
        ).to("sismico.combine")

        return builder.build()
    }

}