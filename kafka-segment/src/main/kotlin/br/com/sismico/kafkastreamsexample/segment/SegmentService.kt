package br.com.sismico.kafkastreamsexample.segment

import com.sismico.kafkastreamsexample.CustomerCreated
import com.sismico.kafkastreamsexample.CustomerSegmented
import com.sismico.kafkastreamsexample.ScoreCalculated
import org.springframework.stereotype.Service

@Service
class SegmentService {
    fun segment(customer: CustomerCreated, score: ScoreCalculated) = CustomerSegmented(
        customer.getUsername(),
        when (score.getScore()) {
            in 800f..999f -> "Prime"
            else -> "Default"
        }
    )
}