package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.sales.network.TerminalCode
import kotlinx.coroutines.launch

class PairTerminalDialogViewModel(salesRepository: SalesRepository) : BaseViewModel(salesRepository) {

    private val _deviceCode: MutableLiveData<Resource<TerminalCode>> = MutableLiveData()
    val deviceCode: LiveData<Resource<TerminalCode>>
        get() = _deviceCode

//     fun getDeviceCode()= viewModelScope.launch {
//         s
//     }

}