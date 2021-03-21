package com.timife.a_n_nursery_app.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentSecondScreenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Second_Screen.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondScreen : Fragment() {
    private lateinit var binding : FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondScreenBinding.inflate(inflater)

        val view = activity?.findViewById<ViewPager2>(R.id.viewPager)
        // Inflate the layout for this fragment
        binding.next2.setOnClickListener {
            view?.currentItem = 2
//            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }
        return binding.root
    }
}