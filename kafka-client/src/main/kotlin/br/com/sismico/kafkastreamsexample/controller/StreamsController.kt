package br.com.sismico.kafkastreamsexample.controller

import br.com.sismico.kafkastreamsexample.producer.AddressProducer
import br.com.sismico.kafkastreamsexample.producer.ClientProducer
import org.springframework.web.bind.annotation.*

@RestController
class StreamsController(
    val clientProducer: ClientProducer,
    val addressProducer: AddressProducer
) {

    @GetMapping("/version")
    fun version() = mapOf("version" to 1)

    @PostMapping("/user")
    fun user(@RequestParam username: String) {
        clientProducer.send(username)
    }

    @PostMapping("/address")
    fun user(
        @RequestParam username: String,
        @RequestParam address: String
    ) {
        addressProducer.send(username, address)
    }
}