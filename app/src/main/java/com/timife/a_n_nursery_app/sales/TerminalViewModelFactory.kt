package com.timife.a_n_nursery_app.sales

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.sales.network.DeviceCode

class TerminalViewModelFactory(
    private val code: DeviceCode,
    private val application:Application,
    private val salesRepository: SalesRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PairTerminalDialogViewModel::class.java)) {
            return PairTerminalDialogViewModel(code,application, salesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}