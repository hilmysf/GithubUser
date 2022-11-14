package com.dicoding.picodiploma.githubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.adapter.UserAdapter
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_favorite.*


class FavoriteActivity : AppCompatActivity() {
    private lateinit var userViewMovel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_title)
        setupAdapter()
        userViewMovel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewMovel.readAllData.observe(this, Observer {
            userAdapter.setData(it)
        })
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        rv_favorite.layoutManager = LinearLayoutManager(applicationContext)
        rv_favorite.adapter = userAdapter
        rv_favorite.setHasFixedSize(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.action_favorite).isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}