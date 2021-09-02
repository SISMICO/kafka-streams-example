//package br.com.sismico.kafkastreamsexample.producer
//
//import br.com.sismico.kafkastreamsexample.entity.Item
//import org.apache.kafka.clients.producer.Producer
//import org.apache.kafka.clients.producer.ProducerRecord
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//
//@Service
//class ItemProducer(
//    val producer: Producer<String, Any?>
//) {
//
//    val log: Logger = LoggerFactory.getLogger(ItemProducer::class.java)
//
//    fun send(username: String, item: String) {
//        log.info("Address $item sent for user $username")
//        producer.send(ProducerRecord(ProducerTopics.ITEMS.topic, Item(username, item))).get()
//    }
//}