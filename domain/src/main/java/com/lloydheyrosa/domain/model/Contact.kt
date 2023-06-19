package com.lloydheyrosa.domain.model

/**
 * The details of a Contact Person.
 */
data class Contact(
    val id: Int,
    val email: String,
    val fullName: String,
    val imageUrl: String
)