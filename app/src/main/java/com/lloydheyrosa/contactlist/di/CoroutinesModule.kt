package com.lloydheyrosa.contactlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    /**
     * Provides the [CoroutineScope] that lasts the whole Application's lifetime.
     */
    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return MainScope()
    }
}