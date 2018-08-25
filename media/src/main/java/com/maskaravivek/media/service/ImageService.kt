package com.maskaravivek.media.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.common.util.concurrent.SettableFuture
import com.google.firebase.storage.StorageReference
import com.maskaravivek.boilerplate.utils.FileUtils
import com.maskaravivek.media.model.MediaObject
import com.maskaravivek.media.model.MediaSource.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageService @Inject
constructor(val transferUtility: TransferUtility,
            val storageReference: StorageReference) {

    private fun getImageStorageReference(path: String): StorageReference {
        return storageReference.child(path)
    }

    @Throws(IOException::class)
    fun getImageFile(bucket: String, mediaObject: MediaObject): Observable<File> {
        val file = FileUtils.getFile(mediaObject.url)
        if (file.exists()) {
            Timber.d("File already exists at %s", file.absolutePath)
            return Observable.fromCallable { file }
        }

        return getDownloadedFile(bucket, mediaObject)
    }

    @SuppressLint("CheckResult")
    @Throws(IOException::class)
    private fun getDownloadedFile(bucket: String, mediaObject: MediaObject): Observable<File> {
        val file = FileUtils.getOrCreateFile(mediaObject.url)
        val future = SettableFuture.create<File>()
        val transferObserver = downloadImage(bucket, mediaObject.url, file)
                ?: return Observable.error(NullPointerException("Error occurred while downloading file!"))

        transferObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    future.set(file)
                } else if (state == TransferState.CANCELED || state == TransferState.FAILED) {
                    future.setException(RuntimeException("Error occurred"))
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {

            }

            override fun onError(id: Int, ex: Exception) {
                future.setException(ex)
            }
        })

        return Observable.fromFuture(future)
    }

    @Throws(IOException::class)
    fun loadImage(imageView: ImageView, mediaObject: MediaObject, callback: ImageLoadCallback) {
        val context = imageView.context
        if (context == null) {
            Timber.d("Context is null")
            return
        }

        val activity = getActivity(context)
        if (activity == null || activity.isFinishing) {
            Timber.d("Activity is null or finishing")
            return
        }

        when (mediaObject.source) {
            STORAGE -> Glide.with(imageView.context)
                    .load(getImageStorageReference(mediaObject.url))
                    .listener(getRequestListener(mediaObject, callback))
                    .into(imageView)
            URL -> Glide.with(imageView.context)
                    .load(mediaObject.url)
                    .listener(getRequestListener(mediaObject, callback))
                    .into(imageView)
            S3 -> getImageFile("word-a-day", mediaObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ file ->
                        Glide.with(imageView.context)
                                .load(file)
                                .listener(getRequestListener(mediaObject, callback))
                                .into(imageView)
                    },
                            { throwable ->
                                Timber.e(throwable, "Error occurred while loading image")
                                callback.onError(throwable)
                            })
            LOCAL -> Glide.with(imageView.context)
                    .load(File(mediaObject.url))
                    .listener(getRequestListener(mediaObject, callback))
                    .into(imageView)
        }
    }

    private fun getActivity(context: Context): Activity? {
        var contextObj = context
        while (contextObj is ContextWrapper) {
            if (contextObj is Activity) {
                return contextObj
            }
            contextObj = contextObj.baseContext
        }
        return null
    }

    private fun getRequestListener(item: MediaObject, callback: ImageLoadCallback): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                callback.onError(e)
                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                val bundle = Bundle()
                bundle.putString("word", item.url)
                callback.onSuccess()
                return false
            }
        }
    }

    private fun downloadImage(bucket: String, fileName: String, file: File): TransferObserver? {
        Timber.d("Downloading image %s to file %s", fileName, file.name)
        return try {
            transferUtility.download(
                    bucket,
                    fileName,
                    file
            )
        } catch (ex: Exception) {
            val bundle = Bundle()
            Timber.e(ex, "Error occurred while downloading from S3")
            bundle.putString("error", ex.message)
            null
        }

    }

    interface ImageLoadCallback {
        fun onSuccess()
        fun onError(throwable: Throwable?)
    }
}