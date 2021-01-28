package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentVendorBinding
import com.timife.a_n_nursery_app.login.ui.visible
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class VendorFragment :  BaseFragment<VendorViewModel, FragmentVendorBinding, VendorRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.getVendorItems()

        viewModel.vendors.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success ->{
                    //adapter.item = it.value

                    Toast.makeText(activity , "Vendors: ${it.value}",Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                //    binding.inventoryProgress.visible(false)
                    Toast.makeText(requireContext(), "Error loading vendors ${it.errorBody}", Toast.LENGTH_LONG)
                            .show()
                }
                is Resource.Loading ->{
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG)
                            .show()

                    //  binding.inventoryProgress.visible(true)
            }

            }
        })

    }



    override fun getViewModel() = VendorViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =  FragmentVendorBinding.inflate(inflater, container, false)


    override fun getRepository(): VendorRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(VendorsApi::class.java, token)
        return VendorRepository(api)
    }

}