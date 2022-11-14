package com.dicoding.picodiploma.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser.data.api.ApiConfig
import com.dicoding.picodiploma.githubuser.data.api.PostResponse
import com.dicoding.picodiploma.githubuser.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val _listUsers = MutableLiveData<List<User?>>()
    val listUsers: LiveData<List<User?>> = _listUsers

    fun setUser(users: String): LiveData<List<User?>> {

        ApiConfig.getApiService().getSearch(users).enqueue(object: Callback<PostResponse> {
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
       return listUsers
    }
}
