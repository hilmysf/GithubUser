package UI

import android.app.SearchManager
import android.content.Context
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser.R
import data.Api
import data.ApiConfig
import data.PostResponse
import data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    val _listUsers = MutableLiveData<List<User?>>()
    val listUsers: LiveData<List<User?>> = _listUsers

    fun setUser(users: String): LiveData<List<User?>> {

        ApiConfig.getApiService().getPosts(users).enqueue(object: Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.e("Success", response.message())
                if (response.isSuccessful){
                    response.body()?.items.let {
                        _listUsers.postValue(it)
                    }
                }
            }
        })
       return _listUsers
    }
}
