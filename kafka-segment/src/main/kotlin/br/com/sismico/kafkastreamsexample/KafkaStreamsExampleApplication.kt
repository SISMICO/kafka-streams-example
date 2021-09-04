package br.com.sismico.kafkastreamsexample

import org.apache.kafka.streams.KafkaStreams
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.CountDownLatch

@SpringBootApplication
class KafkaStreamsExampleApplication(
    val kafkaStream: KafkaStreams
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        Runtime.getRuntime().addShutdownHook(Thread(kafkaStream::close))
        startKafkaStreamsSynchronously(kafkaStream)
    }

    fun startKafkaStreamsSynchronously(streams: KafkaStreams) {
        val latch = CountDownLatch(1)
        streams.setStateListener { newState: KafkaStreams.State, oldState: KafkaStreams.State ->
            if (oldState == KafkaStreams.State.REBALANCING && newState == KafkaStreams.State.RUNNING) {
                latch.countDown()
            }
        }
        streams.cleanUp()
        streams.start()
        try {
            latch.await()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KafkaStreamsExampleApplication>(*args)
}
