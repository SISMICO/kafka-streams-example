package br.com.sismico.kafkastreamsexample.controller

import br.com.sismico.kafkastreamsexample.producer.CustomerProducer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StreamsController(
    val clientProducer: CustomerProducer
) {

    @GetMapping("/version")
    fun version() = mapOf("version" to 1)

    @PostMapping("/user")
    fun user(
        @RequestParam username: String,
        @RequestParam fullname: String,
        @RequestParam document: String
    ) {
        clientProducer.send(username, fullname, document)
    }
}