package br.com.sismico.kafkastreamsexample.stream

enum class ProducerTopics(val topic: String) {
    CLIENT("sismico.client"),
    ADDRESS("sismico.address"),
    ITEMS("sismico.items")
}