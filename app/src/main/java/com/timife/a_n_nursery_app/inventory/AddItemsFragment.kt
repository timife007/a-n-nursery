package com.timife.a_n_nursery_app.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.timife.a_n_nursery_app.databinding.FragmentAddItemsBinding
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryFragment
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationFragment
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationFragment
import com.timife.a_n_nursery_app.inventory.lots.ui.LotFragment


/**
 * A simple [Fragment] subclass.
 * Use the [AddItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemsFragment : Fragment() {
    private lateinit var binding: FragmentAddItemsBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddItemsBinding.inflate(inflater)
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        setupViewPager(tabLayout, viewPager)
        return binding.root
    }

    private fun setupViewPager(tabLayout: TabLayout, viewPager: ViewPager2) {
        val adapter =
            AddItemsPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(CategoryFragment())
        adapter.addFragment(LocationFragment())
        adapter.addFragment(LotFragment())
        adapter.addFragment(ClassificationFragment())
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Categories"
                1 -> tab.text = "Locations"
                2 -> tab.text = "Lots"
                3 -> tab.text = "Classifications"
            }
        }.attach()
    }

}