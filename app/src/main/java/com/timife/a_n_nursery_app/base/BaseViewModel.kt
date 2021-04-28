package com.timife.a_n_nursery_app.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.timife.a_n_nursery_app.user.UserApi

abstract class BaseViewModel(
        private val baseRepository: BaseRepository
) : ViewModel() {
    init{
        Log.d("VIEW_MODEL_TEST","View-model Created")
    }
    suspend fun logout(api: UserApi) = baseRepository.logout(api)
}