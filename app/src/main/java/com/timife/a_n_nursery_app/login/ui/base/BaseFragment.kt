package com.timife.a_n_nursery_app.login.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.timife.a_n_nursery_app.login.UserPreferences
import com.timife.a_n_nursery_app.login.network.LoginRetrofitClient
import com.timife.a_n_nursery_app.login.ui.auth.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, Repo : BaseRepository> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: B
    protected val loginRetrofitClient = LoginRetrofitClient()
    protected lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getLoginRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root

    }


    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getLoginRepository(): Repo
}