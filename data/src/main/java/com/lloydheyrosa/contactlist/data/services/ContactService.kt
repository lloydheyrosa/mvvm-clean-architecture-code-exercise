package com.lloydheyrosa.contactlist.data.services

import com.lloydheyrosa.contactlist.data.model.GetContactsResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContactService {

    @GET("/api/users")
    suspend fun getContacts(
        @Query("page") page: Int,
    ): GetContactsResponse

}