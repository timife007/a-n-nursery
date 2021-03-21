package com.timife.a_n_nursery_app.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentSettingsBinding

class SettingsFragment :
    BaseFragment<SettingsViewModel, FragmentSettingsBinding, SettingsRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.profile.setOnClickListener {
            this.findNavController().navigate(R.id.action_settingsFragment_to_profileFragment)

        }
        binding.logOut.setOnClickListener {
            logout()
        }
        
        binding.accessControl.setOnClickListener{
            this.findNavController().navigate(R.id.action_settingsFragment_to_accessControlFragment)
        }
    }

    override fun getViewModel() = SettingsViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun getRepository(): SettingsRepository {
        return SettingsRepository()
    }

}