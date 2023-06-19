package com.lloydheyrosa.contactlist.data.mapper

import com.lloydheyrosa.contactlist.data.model.GetContactsResponse
import com.lloydheyrosa.domain.model.Contact
import javax.inject.Inject

class ContactsMapper @Inject constructor() : Mapper<GetContactsResponse, List<Contact>> {

    override fun mapFrom(from: GetContactsResponse): List<Contact> {
        return from.data.map {
            Contact(
                id = it.id,
                fullName = "${it.firstName} ${it.lastName}",
                email = it.email,
                imageUrl = it.avatar
            )
        }
    }

}