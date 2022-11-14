package com.dicoding.picodiploma.githubuser.ui.activity

import android.content.Intent
import com.dicoding.picodiploma.githubuser.ui.viewmodel.DetailViewModel
import com.dicoding.picodiploma.githubuser.adapter.SectionsPagerAdapter
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.dicoding.picodiploma.githubuser.ui.viewmodel.UserViewModel
import com.dicoding.picodiploma.githubuser.data.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userViewModel: UserViewModel
    private var isFavorite: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val objekUser = User()
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setupPager()
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        user?.let { getMainViewModel(it) }
        fabOnClick(isFavorite)
        isFavorited(objekUser?.isFavorite)
    }

    private fun fabOnClick(state: Boolean?) {
        fab.setOnClickListener {
            when (state) {
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
    }

    private fun getMainViewModel(user: User) {

        supportActionBar?.title = user.login.toString()
        detailViewModel.setDetail(user.login.toString())
        detailViewModel.users.observe(this, Observer {
        Glide.with(applicationContext)
            .load(it?.avatarUrl)
            .apply(RequestOptions().override(300, 300))
            .into(img_user)
        tv_name_detail.text = it?.name
        tv_username_detail.text = it?.login
        tv_location_detail.text = it?.location
        tv_company_detail.text = it?.company
        isFavorite = user.isFavorite
        isFavorited(user.isFavorite)
        })
    }

    private fun insertDataToDatabase() {
        detailViewModel.users.observe(this, Observer { user ->
            user?.isFavorite = true
            userViewModel.addUser(user!!)
            setFbFavorite(user.isFavorite)
        })
    }

    private fun deleteDataFromDatabase() {
        detailViewModel.users.observe(this, Observer {
            it?.isFavorite = false
            userViewModel.deleteUser(it!!)
            setFbFavorite(it.isFavorite)
        })
    }

    private fun setFbFavorite(state: Boolean?) {
        if (state!!) {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_fill))
        } else {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_border))
        }
    }

    private fun isFavorited(state: Boolean?) {
        if (state!!) {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_fill))
        } else {
            fab.setImageDrawable(getDrawable(R.drawable.favorite_border))
        }

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