package com.timife.a_n_nursery_app.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.ViewModelFactory
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.startNewActivity
import com.timife.a_n_nursery_app.user.UserApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, Repo : BaseRepository> :
    Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: B
    protected val retrofitClient = RetrofitClient()
    protected lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try{

            userPreferences = UserPreferences(requireContext())
            binding = getFragmentBinding(inflater, container)
            val factory = ViewModelFactory(getRepository())
            viewModel = ViewModelProvider(this, factory).get(getViewModel())
            lifecycleScope.launch { userPreferences.authToken.first() }
            setHasOptionsMenu(true)
        }catch (e:Exception){
            Log.d("TAG","onCreateView",e)
        }
        return binding.root

    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = retrofitClient.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        userPreferences.clear()
        requireActivity().startNewActivity(LoginActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): Repo
}