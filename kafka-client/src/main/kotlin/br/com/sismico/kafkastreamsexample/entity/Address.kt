package br.com.sismico.kafkastreamsexample.entity

data class Address(
    val username: String,
    val address: String
) {
    override fun toString(): String {
        return "$username:$address"
    }
}
