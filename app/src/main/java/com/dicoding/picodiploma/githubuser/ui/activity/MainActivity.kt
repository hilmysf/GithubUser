package com.dicoding.picodiploma.githubuser.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.adapter.UserAdapter
import com.dicoding.picodiploma.githubuser.AlarmReceiver
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var alarmReceiver: AlarmReceiver
    var state = true
    private lateinit var actionAlarm: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(getString(R.string.title))
        showRecyclerCardView()
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        rv_user.setHasFixedSize(true)
        alarmReceiver = AlarmReceiver()
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val user = query.toString()
                if (user.isEmpty()) {
                    showLoading(true)
                    tv_empty_message_main.visibility = View.VISIBLE
                    return true
                } else {
                    showLoading(true)
                    mainViewModel.setUser(user)
                    img_es.visibility = View.GONE
                    tv_bigtext.visibility = View.GONE
                    tv_minitext.visibility = View.GONE
                }
                return true
            }
        })
        mainViewModel.listUsers.observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            } else {
                tv_empty_message_main.visibility = View.VISIBLE
            }
        })

    }

    private fun showRecyclerCardView() {
        adapter = UserAdapter()
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        actionAlarm = menu.findItem(R.id.action_set_alarm)
        return super.onCreateOptionsMenu(menu)
    }
    fun updateIcon(state: Boolean) {
        if (state) {
            actionAlarm.setIcon(R.drawable.ic_baseline_notifications_off_24)
        }
        else{
            actionAlarm.setIcon(R.drawable.ic_baseline_notifications_24)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_set_alarm -> {
                val time = "01:55"
                val message = "Bangun bos"
                if (state) {
                    alarmReceiver.setRepeatingAlarm(
                        this, AlarmReceiver.TYPE_REPEATING,
                        time,
                        message
                    )
                    updateIcon(state)
                    state = false
                }
                else{
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
                    updateIcon(state)
                    state = true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}