package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentTerminalBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class TerminalFragment : BaseFragment<TerminalViewModel,FragmentTerminalBinding,SalesRepository>() {

    companion object {
        fun newInstance() = TerminalFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceCode.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                }

                is Resource.Loading ->{
                    showProgressBar()
                }

                is Resource.Failure ->{
                    hideProgressBar()
                    handleApiError(it)
                }
            }
        })
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