package data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("followers_url")
    val followersUrl: String? = null,
    @field:SerializedName("following_url")
    val followingUrl: String? = null,
    @field:SerializedName("organizations_url")
    val organizationsUrl: String? = null,
    @field:SerializedName("repos_url")
    val reposUrl: String? = null
) : Parcelable {
}