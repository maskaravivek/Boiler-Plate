package com.maskaravivek.boilerplate.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.maskaravivek.boilerplate.client.AppClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    private val baseUrl = ""

    @Provides
    @Singleton
    fun provideFirebaseClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryClient(retrofit: Retrofit): AppClient {
        return retrofit.create(AppClient::class.java)
    }
}