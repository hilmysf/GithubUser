package data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getPosts(
        @Query("q")
        username: String,
        @Header("accept")
        api: String = "aebefc09f98dfabea3d51cb4a817bc1e0671268d"
    ): Call<PostResponse>

    @GET("search/users")
    fun getPosts(
        @Path("username")
        username: String?): Call<PostResponse>
}