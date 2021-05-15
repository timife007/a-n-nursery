package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentTerminalBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.AddItemsFragmentDirections
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.network.DeviceCode
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class TerminalFragment : BaseFragment<TerminalViewModel,FragmentTerminalBinding,SalesRepository>() {



    companion object {
        fun newInstance() = TerminalFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.connect.setOnClickListener {
            viewModel.getDeviceCode()

            viewModel.deviceCode.observe(viewLifecycleOwner, Observer { deviceCode ->

                when(deviceCode){
                    is Resource.Success ->{
                        hideProgressBar()
                        Toast.makeText(requireContext(),"$deviceCode",Toast.LENGTH_SHORT).show()
                        val code = DeviceCode(
                            deviceCode.value.device_code.code,
                            deviceCode.value.device_code.status,
                            deviceCode.value.device_code.id,
                            deviceCode.value.device_code.location_id,
                            deviceCode.value.device_code.name,
                            deviceCode.value.device_code.pair_by,
                            deviceCode.value.device_code.product_type,
                            deviceCode.value.device_code.status_changed_at,
                            deviceCode.value.device_code.created_at
                        )
                        if (
                            NavHostFragment.findNavController(this).currentDestination?.id == R.id.terminalFragment
                        ) {

                            this.findNavController().navigate(
                                TerminalFragmentDirections.actionTerminalFragmentToPairTerminalDialog(
                                    code
                                )
                            )
                        }
                    }

                    is Resource.Loading ->{
                        showProgressBar()
                    }

                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(deviceCode)
                    }
                }
            })

        }
    }
    private fun hideProgressBar() {
        binding.terminalProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.terminalProgress.visibility = View.VISIBLE
    }



    override fun getViewModel()=TerminalViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentTerminalBinding.inflate(inflater)

    override fun getRepository(): SalesRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(SalesApi::class.java, token)
        val database = CartDatabase.invoke(requireContext())

        return SalesRepository(api,database)
    }

}