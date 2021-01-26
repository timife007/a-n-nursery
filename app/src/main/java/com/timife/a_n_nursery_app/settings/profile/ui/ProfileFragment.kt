package com.timife.a_n_nursery_app.settings.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentProfileBinding
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.login.ui.visible
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi
import com.timife.a_n_nursery_app.settings.profile.response.Profile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepository>() {
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding.profileProgress.visible(false)
        navController = findNavController()
        val dropdownList = listOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, dropdownList)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.timeZone.setText("Time Zone", false)

        viewModel.getUserProfile()
        viewModel.profile.observe(viewLifecycleOwner,  {
            when (it) {
                is Resource.Success ->
                {binding.profileProgress.visible(false)
                    updateUI(it.value)}
                is Resource.Failure ->{
//                    Toast.makeText(requireContext(),"Error loading profile", Toast.LENGTH_LONG).show()
                    logout()
                }

                is Resource.Loading ->
                    binding.profileProgress.visible(true)
            }
        })
    }

    //TO DO , recall that you want to make a complaint on the edit profile page
    private fun updateUI(profile: Profile) {
        with(binding){
            firstName.setText(profile.first_name, TextView.BufferType.EDITABLE)
            lastName.setText(profile.last_name,TextView.BufferType.EDITABLE)
            profileEmail.setText(profile.email,TextView.BufferType.EDITABLE)
        }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getRepository(): ProfileRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(ProfileApi::class.java, token)
        return ProfileRepository(api)
    }
}

//token