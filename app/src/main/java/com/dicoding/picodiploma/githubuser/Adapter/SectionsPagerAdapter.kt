package com.dicoding.picodiploma.githubuser.Adapter

import com.dicoding.picodiploma.githubuser.UI.Fragment.FollFragment
import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.githubuser.R

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    @StringRes
    private val tabTitles = intArrayOf(
        R.string.tab1,
        R.string.tab2
    )

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment? = FollFragment.newInstance(position + 1)
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }

}