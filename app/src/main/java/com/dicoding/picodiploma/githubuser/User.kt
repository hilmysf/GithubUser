package com.dicoding.picodiploma.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var photo: Int,
    var name: String,
    var username: String,
    var location: String,
    var followers: String,
    var following: String,
    var company: String,
    var repository: String
) : Parcelable {
}