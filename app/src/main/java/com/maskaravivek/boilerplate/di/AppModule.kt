package com.maskaravivek.boilerplate.di

import android.app.Application
import android.content.Context
import com.maskaravivek.media.AwsModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AwsModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}