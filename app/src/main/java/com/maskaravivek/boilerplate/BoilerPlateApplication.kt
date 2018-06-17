package com.maskaravivek.boilerplate

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.v4.app.Fragment
import com.facebook.stetho.Stetho
import com.maskaravivek.boilerplate.di.AppComponent
import com.maskaravivek.boilerplate.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class BoilerPlateApplication : Application(), HasActivityInjector, HasServiceInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build())
            Timber.plant(Timber.DebugTree())
        }
        getAppComponent().inject(this)
    }

    fun getAppComponent(): AppComponent {
        return appComponent ?: DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingActivityInjector
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return dispatchingFragmentInjector
    }

    override fun serviceInjector(): DispatchingAndroidInjector<Service>? {
        return dispatchingServiceInjector
    }

    companion object {
        lateinit var instance: BoilerPlateApplication
        private val appComponent: AppComponent? = null
    }
}