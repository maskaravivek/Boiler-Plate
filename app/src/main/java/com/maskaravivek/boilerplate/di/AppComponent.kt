package com.maskaravivek.boilerplate.di

import android.app.Application
import com.maskaravivek.boilerplate.BoilerPlateApplication
import com.maskaravivek.media.AwsModule
import com.maskaravivek.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    AwsModule::class,
    ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: BoilerPlateApplication)
}