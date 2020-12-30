package UI

import android.app.SearchManager
import android.content.Context
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.githubuser.R
import data.User
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView.LayoutManager as RecyclerViewLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
//    private var users: ArrayList<User> = arrayListOf()
    private var title: String = "Github User's"
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)
        adapter = UserAdapter()
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        rv_user.setHasFixedSize(true)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.searchView) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val user = newText.toString()
                if (user.isEmpty()) {
                    return true
                } else {
                    mainViewModel.setUser(user)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val user = query.toString()
                if (user.isEmpty()) {
                    return true
                } else {
                    mainViewModel.setUser(user)
                }
                return true
            }
        })

        mainViewModel.listUsers.observe(this, Observer {
            adapter.setData(it)
        })

    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}