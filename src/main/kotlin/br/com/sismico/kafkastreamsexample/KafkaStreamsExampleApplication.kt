package br.com.sismico.kafkastreamsexample

import br.com.sismico.kafkastreamsexample.stream.StreamExample
import org.apache.kafka.streams.KafkaStreams
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import java.util.concurrent.CountDownLatch

@SpringBootApplication
class KafkaStreamsExampleApplication(
    val kafkaStream: KafkaStreams
): CommandLineRunner {
    override fun run(vararg args: String?) {
        kafkaStream.cleanUp()
        kafkaStream.start()
        CountDownLatch(1).await()
    }

}

fun main(args: Array<String>) {
    runApplication<KafkaStreamsExampleApplication>(*args)
}
