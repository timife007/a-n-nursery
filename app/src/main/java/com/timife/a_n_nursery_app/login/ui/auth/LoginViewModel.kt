package com.timife.a_n_nursery_app.login.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.login.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel(repository) {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun loginUser(
        password: String,
        email: String
    ) {
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(email, password)
        }
    }

   suspend fun saveAuthToken(token: String)  {
        repository.saveAuthToken(token)
    }
}