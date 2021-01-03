package com.dicoding.picodiploma.githubuser.UI.Activity

import android.content.Intent
import com.dicoding.picodiploma.githubuser.UI.ViewModel.DetailViewModel
import com.dicoding.picodiploma.githubuser.Adapter.SectionsPagerAdapter
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.UI.ViewModel.UserViewModel
import com.dicoding.picodiploma.githubuser.data.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_foll.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userViewModel: UserViewModel
    var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setupPager()
        getMainViewModel()
        fabOnClick()
    }

    private fun fabOnClick() {
        detailViewModel.users.observe(this, Observer { user ->
            fab.setOnClickListener {
                when (user?.isFavorite) {
                    true -> {
                        deleteDataFromDatabase()
                        Snackbar.make(it, R.string.delete_favorite, Snackbar.LENGTH_SHORT).show()
                    }
                    false -> {
                        insertDataToDatabase()
                        Snackbar.make(it, R.string.add_favorite, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun getMainViewModel() {
        val username = intent.getStringExtra(EXTRA_USER)
        supportActionBar?.title = username.toString()
        detailViewModel.setDetail(username.toString())
        detailViewModel.users.observe(this, Observer {
            Glide.with(applicationContext)
                .load(it?.avatarUrl)
                .apply(RequestOptions().override(300, 300))
                .into(img_user)
            tv_name_detail.text = it?.name
            tv_username_detail.text = it?.login
            tv_location_detail.text = it?.location
            tv_company_detail.text = it?.company
            isFavorited()
        })
    }

    private fun insertDataToDatabase() {
        detailViewModel.users.observe(this, Observer { user ->
            user?.isFavorite = true
            userViewModel.addUser(user!!)
            setFbFavorite(user?.isFavorite)
        })
    }

    private fun deleteDataFromDatabase() {
        detailViewModel.users.observe(this, Observer {
            it?.isFavorite = false
            userViewModel.deleteUser(it!!)
            setFbFavorite(it?.isFavorite)
        })
    }

    private fun setFbFavorite(state: Boolean?) {
        if (state!!) {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_fill))
        } else {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_border))
        }
    }

    private fun isFavorited() {
        detailViewModel.users.observe(this, Observer {
            if (it?.isFavorite!!) {
                fab.setImageDrawable(getDrawable(R.drawable.favorite_fill))
            } else {
                fab.setImageDrawable(getDrawable(R.drawable.favorite_border))
            }
        })
    }

    private fun setupPager() {
        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.vp_foll)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
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