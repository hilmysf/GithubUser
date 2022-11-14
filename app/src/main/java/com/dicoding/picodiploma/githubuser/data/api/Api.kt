package com.dicoding.picodiploma.githubuser.data.api

import com.dicoding.picodiploma.githubuser.data.User
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("search/users")
    @Headers("Authorization: token aebefc09f98dfabea3d51cb4a817bc1e0671268d")
    fun getSearch(
        @Query("q")
        username: String
    ): Call<PostResponse>

    @GET("users/{username}")
    @Headers("Authorization: token aebefc09f98dfabea3d51cb4a817bc1e0671268d")
    fun getDetail(
        @Path("username")
        username: String
    ): Call<User>

    @GET("users/{username}/following")
    @Headers("Authorization: token aebefc09f98dfabea3d51cb4a817bc1e0671268d")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token aebefc09f98dfabea3d51cb4a817bc1e0671268d")
    fun getFollower(
        @Path("username")
        username: String
    ): Call<List<User>>
}