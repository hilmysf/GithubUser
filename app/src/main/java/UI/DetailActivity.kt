package UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import com.google.android.material.tabs.TabLayout
import data.User
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_cardview_user.view.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        setupPager()
        getMainViewModel()

    }

    fun getMainViewModel() {
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
        })
    }

    fun setupPager() {
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
}