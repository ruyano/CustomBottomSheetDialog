package com.example.bottomsheetdialog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Account(var id: Int,
              var name: String,
              var email: String) : Parcelable {

    override fun toString(): String {
        return "name='$name'\nemail='$email'"
    }

}