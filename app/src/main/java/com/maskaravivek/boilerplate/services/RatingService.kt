package com.maskaravivek.boilerplate.services

import android.app.Activity
import com.codemybrainsout.ratingdialog.RatingDialog
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatingService @Inject
constructor() {

    fun setRecurringRatingPrompt(activity: Activity, sessions: Int) {
        val ratingDialog = RatingDialog.Builder(activity)
                .threshold(3f)
                .session(sessions)
                .onRatingBarFormSumbit { feedback -> Timber.d(feedback) }.build()

        ratingDialog.show()
    }

    fun showRatingDialog(activity: Activity) {
        RatingDialog.Builder(activity)
                .threshold(3f)
                .onRatingBarFormSumbit { feedback -> Timber.d(feedback) }.build().show()
    }
}
