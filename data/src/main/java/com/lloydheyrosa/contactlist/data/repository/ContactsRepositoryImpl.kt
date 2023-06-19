package com.lloydheyrosa.contactlist.data.repository

import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.contactlist.data.mapper.ContactsMapper
import com.lloydheyrosa.contactlist.data.services.ContactService
import com.lloydheyrosa.contactlist.data.utils.wrapResultValue
import com.lloydheyrosa.domain.model.Contact
import com.lloydheyrosa.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepositoryImpl @Inject constructor(
    private val service: ContactService,
    private val mapper: ContactsMapper
) : ContactsRepository {

    companion object {
        private const val TAG = "ContactsRepository"
    }

    override suspend fun getContactList(page: Int): ApiResult<List<Contact>, Unit> {
        return wrapResultValue(TAG) {
            val response = service.getContacts(page)
            mapper.mapFrom(response)
        }
    }
}