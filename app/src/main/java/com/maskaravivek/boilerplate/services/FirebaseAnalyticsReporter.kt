package com.maskaravivek.boilerplate.services

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsReporter @Inject
constructor(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics

    init {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun setUserIdentifiers(userId: String, corpId: String) {
        firebaseAnalytics.setUserProperty("userId", userId)
    }

    fun logEvent(eventName: String, bundle: Bundle?) {
        var bundle = bundle
        if (bundle == null) {
            bundle = Bundle()
        }

        firebaseAnalytics.logEvent(eventName, bundle)
    }

    fun logEvent(eventName: String) {
        firebaseAnalytics.logEvent(eventName, null)
    }
}