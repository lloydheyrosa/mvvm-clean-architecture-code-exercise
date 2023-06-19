package com.lloydheyrosa.contactlist.data.di

import com.lloydheyrosa.contactlist.data.services.ContactService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideService(@ContactsApi retrofit: Retrofit): ContactService {
        return retrofit.create()
    }
}