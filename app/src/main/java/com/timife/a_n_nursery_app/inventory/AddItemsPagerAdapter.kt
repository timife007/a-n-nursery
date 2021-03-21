package com.timife.a_n_nursery_app.inventory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AddItemsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    private val mFragmentList : ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList : ArrayList<String> = ArrayList()

    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

}