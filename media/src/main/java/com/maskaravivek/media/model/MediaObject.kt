package com.maskaravivek.media.model

import android.os.Parcel
import android.os.Parcelable

data class MediaObject(val url: String, val type: MediaType, val source: MediaSource) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    constructor(url: String, type: String, source: String) : this(url, MediaType.valueOf(type), MediaSource.valueOf(source))

    constructor() : this("", MediaType.NONE, MediaSource.LOCAL)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaObject> {
        override fun createFromParcel(parcel: Parcel): MediaObject {
            return MediaObject(parcel)
        }

        override fun newArray(size: Int): Array<MediaObject?> {
            return arrayOfNulls(size)
        }
    }
}