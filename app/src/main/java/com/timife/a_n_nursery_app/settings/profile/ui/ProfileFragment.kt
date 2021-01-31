package com.timife.a_n_nursery_app.settings.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentProfileBinding
import com.timife.a_n_nursery_app.login.ui.handleApiError
import com.timife.a_n_nursery_app.login.ui.visible
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi
import com.timife.a_n_nursery_app.settings.profile.network.UserName
import com.timife.a_n_nursery_app.settings.profile.response.Profile
import com.timife.a_n_nursery_app.settings.profile.response.ProfileItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding, ProfileRepository>() {
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileProgress.visible(false)
        navController = findNavController()
        val dropdownList = listOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, dropdownList)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.timeZone.setText("Time Zone", false)



        viewModel.getUserProfile()
        viewModel.profile.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    binding.profileProgress.visible(false)
                    updateUI(it.value)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                    logout()
                }

                is Resource.Loading ->
                    binding.profileProgress.visible(true)
            }
        })


            viewModel.update.observe(viewLifecycleOwner,  {
                binding.profileProgress.visible(it is Resource.Loading)
                when(it){
                    is Resource.Success ->{
                        binding.profileProgress.visible(false)
                        Toast.makeText(requireContext(),"Profile Successfully Updated!",Toast.LENGTH_SHORT).show()
                        updateUI(it.value)
                    }
                    is Resource.Failure ->{
                        handleApiError(it){
                            updateProfile()
                        }
                        binding.profileProgress.visible(false)
                    }
                    is Resource.Loading ->{
                        binding.profileProgress.visible(true)
                    }
                }
            })

        binding.update.setOnClickListener {
            updateProfile()
        }

    }




    //TO DO , recall that you want to make a complaint on the edit profile page
    private fun updateUI(profile: ProfileItem) {
        with(binding) {
            firstName.setText(profile.user.first_name, TextView.BufferType.EDITABLE)
            lastName.setText(profile.user.last_name, TextView.BufferType.EDITABLE)
            profileEmail.setText(profile.user.email, TextView.BufferType.EDITABLE)
            phoneNumber.setText(profile.phone_number,TextView.BufferType.EDITABLE)
        }
    }

    fun updateProfile(){
       val userName   =UserName (
               binding.firstName.text.toString().trim(),
                binding.lastName.text.toString().trim()
               )
        val phoneNumber = binding.phoneNumber.text.toString().trim()
        viewModel.updateUserProfile(userName,phoneNumber)
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