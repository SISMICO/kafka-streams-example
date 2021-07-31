package br.com.sismico.kafkastreamsexample.entity

class Item(val username: String, val item: String) {
    override fun toString(): String {
        return "$username:$item"
    }
}
