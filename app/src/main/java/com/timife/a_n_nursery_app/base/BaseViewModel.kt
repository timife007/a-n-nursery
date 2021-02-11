package com.timife.a_n_nursery_app.base

import androidx.lifecycle.ViewModel
import com.timife.a_n_nursery_app.user.UserApi

abstract class BaseViewModel(
        private val baseRepository: BaseRepository
) : ViewModel() {
    suspend fun logout(api: UserApi) = baseRepository.logout(api)
}