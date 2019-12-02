package com.spectrum.task.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("age")
    var age: Int,
    @SerializedName("email")
    var email: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: Name,
    @SerializedName("phone")
    var phone: String,
    var isFav: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        TODO("name"),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(age)
        parcel.writeString(email)
        parcel.writeString(id)
        parcel.writeString(phone)
        parcel.writeByte(if (isFav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Member> {
        override fun createFromParcel(parcel: Parcel): Member {
            return Member(parcel)
        }

        override fun newArray(size: Int): Array<Member?> {
            return arrayOfNulls(size)
        }
    }
}