package com.amitverma.newsapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class Language(val id: String?, val name: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<Language> {
        override fun createFromParcel(parcel: Parcel): Language {
            return Language(parcel)
        }

        override fun newArray(size: Int): Array<Language?> {
            return arrayOfNulls(size)
        }
    }

}
