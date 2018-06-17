package com.maskaravivek.boilerplate.di

import com.maskaravivek.boilerplate.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity
}