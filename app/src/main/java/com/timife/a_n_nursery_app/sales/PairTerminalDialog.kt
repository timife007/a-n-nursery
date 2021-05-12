package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogPairTerminalBinding
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PairTerminalDialog : BaseDialogFragment<PairTerminalDialogViewModel,DialogPairTerminalBinding,SalesRepository>() {

    companion object {
        fun newInstance() = PairTerminalDialog()
    }

    override fun getViewModel()= PairTerminalDialogViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= DialogPairTerminalBinding.inflate(inflater)

    override fun getRepository(): SalesRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(SalesApi::class.java, token)
        val database = CartDatabase.invoke(requireContext())

        return SalesRepository(api,database)
    }

}