package com.qi.responseapp

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class StudentModel(var studentId : Int,var studentname : String?,var studentphone : String?,var studentEmail : String?,var password : String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(studentId)
        parcel.writeString(studentname)
        parcel.writeString(studentphone)
        parcel.writeString(studentEmail)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentModel> {
        override fun createFromParcel(parcel: Parcel): StudentModel {
            return StudentModel(parcel)
        }

        override fun newArray(size: Int): Array<StudentModel?> {
            return arrayOfNulls(size)
        }
    }
}