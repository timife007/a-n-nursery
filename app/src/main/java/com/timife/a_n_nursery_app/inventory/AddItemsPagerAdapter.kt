package com.timife.a_n_nursery_app.inventory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddItemsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragments: Fragment) {
        mFragmentList.add(fragments)
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}