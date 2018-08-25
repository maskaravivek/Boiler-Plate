package com.maskaravivek.boilerplate.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import timber.log.Timber

class UrlUtils {
    companion object {
        fun handleWebUrl(context: Context, url: Uri) {
            Timber.d("Launching web url %s", url.toString())
            val browserIntent = Intent(Intent.ACTION_VIEW, url)
            if (browserIntent.resolveActivity(context.packageManager) == null) {
                return
            }

            val builder = CustomTabsIntent.Builder()
            builder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            val customTabsIntent = builder.build()
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            customTabsIntent.launchUrl(context, url)
        }

        fun handlePlaystoreUrl(context: Context, packageName: String) {
            val uri = Uri.parse("market://details?id=$packageName")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                context.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
            }

        }
    }
}