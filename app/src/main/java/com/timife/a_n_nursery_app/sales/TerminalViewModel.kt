package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.sales.network.DeviceCode
import com.timife.a_n_nursery_app.sales.network.TerminalCode
import kotlinx.coroutines.launch

class TerminalViewModel(private val salesRepository: SalesRepository) : BaseViewModel(salesRepository) {
    private val _deviceCode: MutableLiveData<Resource<TerminalCode>> = MutableLiveData()
    val deviceCode: LiveData<Resource<TerminalCode>>
        get() = _deviceCode

    private val _navigateToPairTerminal = MutableLiveData<DeviceCode>()
    val navigateToPairTerminal: LiveData<DeviceCode>
        get() = _navigateToPairTerminal



//    init {
//        getDeviceCode()
//    }

     fun getDeviceCode()= viewModelScope.launch {
         _deviceCode.value = salesRepository.getTerminalCode()
     }

//    fun getDeviceCodeComplete() {
//        _deviceCode.value = null
//    }
}