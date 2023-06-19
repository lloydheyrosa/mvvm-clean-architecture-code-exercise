package com.lloydheyrosa.domain.repository

import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    suspend fun getContactList(page: Int): ApiResult<List<Contact>, Unit>

}