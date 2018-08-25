package com.maskaravivek.boilerplate.services

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsReporter @Inject
constructor(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun setUserIdentifiers(userId: String) {
        firebaseAnalytics.setUserProperty("userId", userId)
    }

    fun logEvent(eventName: String, bundle: Bundle?) {
        var attributes = bundle
        if (attributes == null) {
            attributes = Bundle()
        }

        firebaseAnalytics.logEvent(eventName, attributes)
    }

    fun logEvent(eventName: String) {
        firebaseAnalytics.logEvent(eventName, null)
    }
}