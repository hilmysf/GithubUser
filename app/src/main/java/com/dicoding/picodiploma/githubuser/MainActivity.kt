package com.dicoding.picodiploma.githubuser

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager as RecyclerViewLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var rvUser: RecyclerView
    private var users: ArrayList<User> = arrayListOf()
    private lateinit var dataName: Array<String>
    private lateinit var dataUserName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private var title: String = "Github User's"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)
        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        adapter = UserAdapter(users)
        rvUser.adapter = adapter

        prepare()
        addItem()
        var layoutManager: RecyclerViewLayoutManager = LinearLayoutManager(this@MainActivity)
        rvUser.layoutManager = layoutManager
        rvUser.adapter = adapter
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.name)
        dataUserName = resources.getStringArray(R.array.username)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataLocation = resources.getStringArray(R.array.location)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataCompany = resources.getStringArray(R.array.company)
        dataRepository = resources.getStringArray(R.array.repository)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataPhoto.getResourceId(position, -1),
                dataName[position],
                dataUserName[position],
                dataLocation[position],
                dataFollowers[position],
                dataFollowing[position],
                dataCompany[position],
                dataRepository[position]
            )
            users.add(user)
        }
        adapter.users = users
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}