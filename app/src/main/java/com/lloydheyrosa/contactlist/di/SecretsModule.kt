package com.lloydheyrosa.contactlist.di

import android.content.Context
import com.lloydheyrosa.contactlist.R
import com.lloydheyrosa.domain.model.ApiParameters
import com.lloydheyrosa.domain.model.ContactsApiParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecretsModule {

    @Singleton
    @Provides
    fun provideGrailApiParameters(@ApplicationContext context: Context): ApiParameters {
        return ContactsApiParameters(
            baseUrl = context.getString(R.string.base_url),
        )
    }
}