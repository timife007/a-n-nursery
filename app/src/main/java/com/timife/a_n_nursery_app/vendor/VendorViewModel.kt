package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import kotlinx.coroutines.launch

class VendorViewModel(
    private val vendorRepository: VendorRepository
) : BaseViewModel(vendorRepository){

    var vendorPage = 1

    private val _vendors: MutableLiveData<Resource<List<VendorItem>>> = MutableLiveData()
    val vendors: LiveData<Resource<List<VendorItem>>>
        get() = _vendors

    init{
        getVendorItems()
    }

    fun getVendorItems() =viewModelScope.launch {
        _vendors.value= Resource.Loading
        val itemsResult = vendorRepository.getVendors(vendorPage)

        _vendors.value = itemsResult
    }


}