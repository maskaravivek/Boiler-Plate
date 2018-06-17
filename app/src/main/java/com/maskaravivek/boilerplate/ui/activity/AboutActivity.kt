package com.maskaravivek.boilerplate.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.perf.metrics.AddTrace
import com.maskaravivek.boilerplate.BuildConfig
import com.maskaravivek.boilerplate.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

open class AboutActivity : AppCompatActivity() {
    @AddTrace(name = "onAboutActivityCreateTrace", enabled = true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aboutApp())
    }

    private fun aboutApp(): View {
        return AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)
                .addItem(getVersionElement())
                .addPlayStore("com.maskaravivek.wordaday")
                .addGroup("Connect with us")
                .addEmail("maskaravivek@gmail.com")
                .addWebsite("http://maskaravivek.com/")
                .setDescription(getString(R.string.app_description))
                .create()
    }

    private fun getVersionElement(): Element {
        var versionElement = Element()
        versionElement.title = "Version " + BuildConfig.VERSION_NAME
        return versionElement
    }
}
