package br.com.sismico.kafkastreamsexample.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CreditController {
    @GetMapping("/version")
    fun version() = mapOf("version" to 1)
}