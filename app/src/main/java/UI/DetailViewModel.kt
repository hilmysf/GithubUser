package UI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.ApiConfig
import data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val _users = MutableLiveData<User?>()
    val users: LiveData<User?> = _users
    val _listUsers = MutableLiveData<List<User?>>()
    val listUsers: LiveData<List<User?>> = _listUsers

    fun setDetail(users: String): LiveData<User?> {
        ApiConfig.getApiService().getDetail(users).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("Success", response.message())
                if (response.isSuccessful) {
                    response.body()?.let {
                        _users.value = it
                    }
                }
            }
        })
        return this.users
    }

    fun setFollowing(users: String): LiveData<List<User?>> {
        ApiConfig.getApiService().getFollowing(users).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.e("Success", response.message())
                if (response.isSuccessful) {
                    response.body()?.let {
                        _listUsers.postValue(it)
                    }
                }
            }
        })
        return listUsers
    }

    fun setFollowers(users: String): LiveData<List<User?>> {
        ApiConfig.getApiService().getFollower(users).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.e("Success", response.message())
                if (response.isSuccessful) {
                    response.body()?.let {
                        _listUsers.postValue(it)
                    }
                }
            }
        })
        return listUsers
    }
}