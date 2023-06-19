package com.lloydheyrosa.domain.model

sealed interface ApiParameters {
    val baseUrl: String
}

data class ContactsApiParameters(
    override val baseUrl: String,
) : ApiParameters