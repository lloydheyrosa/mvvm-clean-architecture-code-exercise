package com.lloydheyrosa.contactlist.di

import com.lloydheyrosa.contactlist.data.repository.ContactsRepositoryImpl
import com.lloydheyrosa.domain.repository.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindingModule {

    @Binds
    fun contactsRepository(impl: ContactsRepositoryImpl): ContactsRepository

}