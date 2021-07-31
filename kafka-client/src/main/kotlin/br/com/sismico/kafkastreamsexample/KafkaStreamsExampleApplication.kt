package br.com.sismico.kafkastreamsexample

import org.apache.kafka.streams.KafkaStreams
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.CountDownLatch

@SpringBootApplication
class KafkaStreamsExampleApplication() {}

fun main(args: Array<String>) {
    runApplication<KafkaStreamsExampleApplication>(*args)
}
