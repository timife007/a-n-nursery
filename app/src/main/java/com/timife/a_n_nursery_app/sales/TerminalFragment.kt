package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentTerminalBinding
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class TerminalFragment : BaseFragment<TerminalViewModel,FragmentTerminalBinding,SalesRepository>() {

    companion object {
        fun newInstance() = TerminalFragment()
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