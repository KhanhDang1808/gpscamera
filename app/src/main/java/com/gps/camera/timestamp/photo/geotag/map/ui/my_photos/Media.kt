package com.gps.camera.timestamp.photo.geotag.map.ui.my_photos

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Media(
    val id: Long,
    val uri: Uri?,
    val path: String?,
    val name: String?,
    val folderName: String?,
    val size: String?,
    val mimeType: String?,
    val width: String?,
    val height: String?,
    val date: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeParcelable(uri, flags)
        parcel.writeString(path)
        parcel.writeString(name)
        parcel.writeString(folderName)
        parcel.writeString(size)
        parcel.writeString(mimeType)
        parcel.writeString(width)
        parcel.writeString(height)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Media> {
        override fun createFromParcel(parcel: Parcel): Media {
            return Media(parcel)
        }

        override fun newArray(size: Int): Array<Media?> {
            return arrayOfNulls(size)
        }
    }

}