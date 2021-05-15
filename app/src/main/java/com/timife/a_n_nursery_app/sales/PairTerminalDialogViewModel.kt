package com.timife.a_n_nursery_app.sales

import android.app.Application
import androidx.lifecycle.*
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.sales.network.DeviceCode
import com.timife.a_n_nursery_app.sales.network.TerminalCode
import kotlinx.coroutines.launch

class PairTerminalDialogViewModel(code:DeviceCode, application: Application,
                                  private val salesRepository: SalesRepository) : AndroidViewModel(application) {

    private val _deviceCode: MutableLiveData<DeviceCode> = MutableLiveData()
    val deviceCode: LiveData<DeviceCode>
        get() = _deviceCode


    init {
        _deviceCode.value = code
    }
}