package com.lloydheyrosa.contactlist.data.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.lloydheyrosa.domain.model.ApiParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.DayOfWeek
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkAccessModule {

    @ContactsApi
    @Provides
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .registerModule(
                KotlinModule.Builder()
                    .configure(KotlinFeature.NullIsSameAsDefault, true)
                    .configure(KotlinFeature.NullToEmptyMap, true)
                    .configure(KotlinFeature.NullToEmptyCollection, true)
                    .build(),
            )
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @ContactsApi
    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @ContactsApi
    @Singleton
    @Provides
    fun provideRetrofit(
        @ContactsApi okHttpClient: OkHttpClient,
        @ContactsApi mapper: ObjectMapper,
        apiParameters: ApiParameters,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiParameters.baseUrl)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(okHttpClient)
            .build()
    }
}