package com.timife.a_n_nursery_app.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
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
    private lateinit var binding : FragmentAddItemsBinding
    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddItemsBinding.inflate(inflater)
        viewPager = binding.viewPager
        setupViewPager(viewPager)
        tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = AddItemsPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(CategoryFragment(),"Categories")
        adapter.addFragment(LocationFragment(),"Locations")
        adapter.addFragment(LotFragment(),"Lots")
        adapter.addFragment(ClassificationFragment(),"Classifications")
        viewPager.adapter = adapter

    }

}