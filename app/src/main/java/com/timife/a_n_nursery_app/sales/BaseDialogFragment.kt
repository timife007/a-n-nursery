package com.timife.a_n_nursery_app.sales

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.ViewModelFactory
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.startNewActivity
import com.timife.a_n_nursery_app.user.UserApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseDialogFragment <VM : BaseViewModel, B : ViewBinding, Repo : BaseRepository> :
    DialogFragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: B
    protected val retrofitClient = RetrofitClient()
    protected lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)

        try{
            userPreferences = UserPreferences(requireContext())
            binding = getFragmentBinding(inflater, container)
            val factory = ViewModelFactory(getRepository())
            viewModel = ViewModelProvider(requireActivity(), factory).get(getViewModel())
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
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): Repo
}