package com.dicoding.picodiploma.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val name = user.name.toString()
        val username = user.username.toString()
        val follower = user.followers.toString()
        val following = user.following.toString()
        val company = user.company.toString()
        val location = user.location.toString()
        val repository = user.repository.toString()
        val photo = user.photo

        tv_name_detail.text = name
        tv_username_detail.text = username
        tv_following.text = following
        tv_company_detail.text = company
        tv_location_detail.text = location
        tv_repository.text = repository
        tv_followers.text = follower
        Glide.with(this)
            .load(photo)
            .into(img_user)
        supportActionBar?.title = name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}