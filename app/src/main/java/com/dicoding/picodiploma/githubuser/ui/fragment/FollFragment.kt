package com.dicoding.picodiploma.githubuser.ui.fragment

import com.dicoding.picodiploma.githubuser.adapter.DetailAdapter
import com.dicoding.picodiploma.githubuser.ui.viewmodel.DetailViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser.R
import kotlinx.android.synthetic.main.fragment_foll.*

class FollFragment : Fragment() {
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USER = "extra_username"

        @JvmStatic
        fun newInstance(index: Int) =
            FollFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }
        setupAdapter()
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        if (index == 1) {
            getFollowingViewModel()
        } else {
            getFollowersViewModel()
        }
    }

    private fun setupAdapter() {
        detailAdapter = DetailAdapter()
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = detailAdapter
        rv_following.setHasFixedSize(true)
    }

    private fun getFollowersViewModel() {
        if (arguments != null) {
            val username = activity?.intent?.getStringExtra(EXTRA_USER)
            detailViewModel.setFollowers(username.toString())
            detailViewModel.listUsers.observe(this, Observer {
                detailAdapter.setData(it)
                showLoading(false)
            })
        } else {
            tv_empty_message.visibility = View.VISIBLE
        }
    }

    private fun getFollowingViewModel() {
        showLoading(true)
        if (arguments != null) {
            val username = activity?.intent?.getStringExtra(EXTRA_USER)
            detailViewModel.setFollowing(username.toString())
            detailViewModel.listUsers.observe(this, Observer {
                detailAdapter.setData(it)
                showLoading(false)
            })
        } else {
            tv_empty_message.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}