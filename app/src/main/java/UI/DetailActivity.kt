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

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

//    private lateinit var detailAdapter: DetailAdapter
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        detailAdapter = DetailAdapter()
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.vp_foll)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f

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
//        val moveDetail = Intent(this, DetailActivity::class.java)
//        moveDetail.putExtra(EXTRA_USER, username.toString())
//        startActivity(moveDetail)
//
//        val newBundle = Bundle()
//        newBundle.putString(FollowingFragment.EXTRA_USER, username.toString())
//        val objects = FollowingFragment()
//        objects.arguments = newBundle

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}