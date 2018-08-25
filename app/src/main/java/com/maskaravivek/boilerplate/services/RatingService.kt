package com.maskaravivek.boilerplate.services

import android.app.Activity
import com.codemybrainsout.ratingdialog.RatingDialog
import com.maskaravivek.boilerplate.utils.UrlUtils
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatingService @Inject
constructor(private val firebaseAnalyticsReporter: FirebaseAnalyticsReporter) {

    fun setRecurringRatingPrompt(activity: Activity, sessions: Int) {
        val ratingDialog = RatingDialog.Builder(activity)
                .threshold(3f)
                .session(sessions)
                .onRatingBarFormSumbit { feedback -> Timber.d(feedback) }.build()

        ratingDialog.show()
    }

    fun rateApp(activity: Activity, directRating: Boolean) {
        if (directRating) {
            directPlayStoreRating(activity)
        } else {
            showRatingDialog(activity)
        }
    }

    private fun showRatingDialog(activity: Activity) {
        firebaseAnalyticsReporter.logEvent("Rating_Dialog_Shown")
        RatingDialog.Builder(activity)
                .threshold(3f)
                .onRatingBarFormSumbit { feedback ->
                    Timber.d(feedback)
                    firebaseAnalyticsReporter.logEvent("Feedback_Submitted")
                }
                .onRatingChanged { _, _ -> firebaseAnalyticsReporter.logEvent("Rating_Changed") }
                .build().show()
    }

    private fun directPlayStoreRating(activity: Activity) {
        firebaseAnalyticsReporter.logEvent("Rating_Play_Store")
        UrlUtils.handlePlaystoreUrl(activity, activity.packageName)
    }
}