package com.lloydheyrosa.contactlist.data.model

data class GetContactsResponse(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<Contact>
) {

    data class Contact(
        val id: Int,
        val email: String,
        val firstName: String,
        val lastName: String,
        val avatar: String
    )

}