package com.timife.a_n_nursery_app.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.timife.a_n_nursery_app.MainActivity
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.FragmentThirdScreenBinding
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.startNewActivity

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdScreen : Fragment() {
    private lateinit var binding : FragmentThirdScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdScreenBinding.inflate(inflater)

        binding.finish.setOnClickListener {
            val userPreferences = UserPreferences(requireContext())
            userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
                val activity =  LoginActivity::class.java
                requireActivity().startNewActivity(activity)
                onBoardingFinished()
                requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            }
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