package com.timife.a_n_nursery_app.onboarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.MainActivity
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.FragmentSplashBinding
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.startNewActivity

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater)

        Handler().postDelayed({
            if (onBoardingFinished()){
                val userPreferences = UserPreferences(requireContext())
                userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
                    val activity = if (it == null) LoginActivity::class.java else MainActivity::class.java
                    requireActivity().startNewActivity(activity)
                    requireActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                }
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        },3000
        )
        return binding.root
    }


    private fun onBoardingFinished():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
}