package com.lloydheyrosa.contactlist.data.di

import com.lloydheyrosa.contactlist.data.component.ResultWrapperImpl
import com.lloydheyrosa.domain.component.ResultWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataUtilsBindingModule {

    @Binds
    fun resultWrapper(impl: ResultWrapperImpl): ResultWrapper
}