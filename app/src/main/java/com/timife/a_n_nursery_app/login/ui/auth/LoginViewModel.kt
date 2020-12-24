package com.timife.a_n_nursery_app.login.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.login.Resource
import com.timife.a_n_nursery_app.login.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginResponse.value = repository.login(email, password)
        }
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}