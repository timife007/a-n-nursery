package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.DialogPairTerminalBinding
import com.timife.a_n_nursery_app.databinding.SalesBttmShtFragmentBinding
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PairTerminalDialog : DialogFragment() {

    private lateinit var binding: DialogPairTerminalBinding
    private lateinit var repository: SalesRepository
    protected val retrofitClient = RetrofitClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(SalesApi::class.java, token)
        val database = CartDatabase.invoke(requireContext())
        repository = SalesRepository(api, database)
        val application = requireNotNull(activity).application
        binding = DialogPairTerminalBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val code = PairTerminalDialogArgs.fromBundle(requireArguments()).deviceCode
        val viewModelFactory = TerminalViewModelFactory(code, application,repository)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(PairTerminalDialogViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}