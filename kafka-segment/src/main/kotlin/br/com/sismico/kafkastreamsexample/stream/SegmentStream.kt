package br.com.sismico.kafkastreamsexample.stream

import br.com.sismico.kafkastreamsexample.config.CUSTOMER_CREATED
import br.com.sismico.kafkastreamsexample.config.CUSTOMER_SEGMENTED
import br.com.sismico.kafkastreamsexample.config.SCORE_CALCULATED
import br.com.sismico.kafkastreamsexample.segment.SegmentService
import com.sismico.kafkastreamsexample.CustomerCreated
import com.sismico.kafkastreamsexample.ScoreCalculated
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class SegmentStream(
    val segmentService: SegmentService
) {
    val log: Logger = LoggerFactory.getLogger(SegmentStream::class.java)

    @Bean
    fun build(): Topology {
        val builder = StreamsBuilder()

        // Subscribe to primary topics
        val customer: KStream<String, CustomerCreated> = builder.stream(CUSTOMER_CREATED)
        val score: KStream<String, ScoreCalculated> = builder.stream(SCORE_CALCULATED)

        // Map a key to join the streams
        val streamCustomer = customer
            .selectKey({ _, value -> value.getDocument() }, Named.`as`("customer_document_key"))
        val streamScore = score
            .selectKey({ _, value -> value.getDocument() }, Named.`as`("score_document_key"))

        // Group Customer
//        val customerGrouped = streamCustomer.groupByKey().aggregate(
//            { -> mutableMapOf<String, Long>() },
//            { _, _, oldValue -> oldValue + 1},
//            Materialized.`as`("customer_aggregated")
//        ).toStream()

        //customerGrouped.to("customer_aggregated")
        streamCustomer
            .groupByKey()
            .count(Named.`as`("customer_aggregated"))
            .toStream()
            .print(Printed.toSysOut())

        // Join Customer with Score to Segment the Customer
        streamCustomer.join(
            streamScore,
            { left: CustomerCreated, right: ScoreCalculated ->
                segmentService.segment(left, right)
            },
            JoinWindows.of(Duration.ofMinutes(10L)),
            StreamJoined.`as`("customer_with_score")
        ).to(CUSTOMER_SEGMENTED)

        // Build and run ...
        return builder.build()
    }

}