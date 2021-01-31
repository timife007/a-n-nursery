package com.timife.a_n_nursery_app.login.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.MainActivity
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentLoginBinding
import com.timife.a_n_nursery_app.login.network.LoginApi
import com.timife.a_n_nursery_app.login.ui.enable
import com.timife.a_n_nursery_app.login.ui.handleApiError
import com.timife.a_n_nursery_app.login.ui.startNewActivity
import com.timife.a_n_nursery_app.login.ui.visible
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.forgotPassword.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.loginprogress.visible(false)
        binding.fab.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            binding.loginprogress.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.access)
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it) {
                        login()
                    }
                }
            }
        })

        binding.editPassword.addTextChangedListener {
            val email = binding.editEmail.text.toString().trim()
            binding.fab.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }
        binding.fab.setOnClickListener {
            login()
        }
    }

    fun login() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        //@todo add user validation(token)
        viewModel.loginUser(password, email)
    }

    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getRepository() =
        LoginRepository(retrofitClient.buildApi(LoginApi::class.java), userPreferences)
}