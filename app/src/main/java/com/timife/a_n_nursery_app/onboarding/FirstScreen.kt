package com.timife.a_n_nursery_app.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentFirstScreenBinding
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.startNewActivity

/**
 * A simple [Fragment] subclass.
 * Use the [First_Screen.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstScreen : Fragment() {
    private lateinit var binding : FragmentFirstScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(inflater)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.next.setOnClickListener {
            viewPager?.currentItem = 1
//            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }

        binding.skip1.setOnClickListener {
            val activity =  LoginActivity::class.java
            requireActivity().startNewActivity(activity)
            onBoardingFinished()
            requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}