package com.lloydheyrosa.domain.usecase

import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.domain.model.Contact
import com.lloydheyrosa.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactsUseCase @Inject constructor(
    private val repository: ContactsRepository
) {

    suspend fun getContacts(page: Int = 1): ApiResult<List<Contact>, Unit> = repository.getContactList(page)


}