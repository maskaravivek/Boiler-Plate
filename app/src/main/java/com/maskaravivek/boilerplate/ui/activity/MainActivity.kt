package com.maskaravivek.boilerplate.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maskaravivek.boilerplate.R
import com.maskaravivek.media.model.MediaObject
import com.maskaravivek.media.model.MediaSource
import com.maskaravivek.media.model.MediaType
import com.maskaravivek.media.service.ImageService
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val imageUrl = "https://firebasestorage.googleapis.com/v0/b/words-cd928.appspot.com/o/assets%2Fstar.png?alt=media&token=d03ba3a2-24ae-438b-a182-65f8c1efabd5"

    @Inject
    lateinit var imageService: ImageService

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageService.loadImage(home_image, MediaObject(imageUrl, MediaType.IMAGE, MediaSource.STORAGE), object: ImageService.ImageLoadCallback {
            override fun onSuccess() {
                Timber.d("Image loaded")
            }

            override fun onError(throwable: Throwable?) {
                Timber.e(throwable,"Image not loaded")
            }

        })
    }
}
