package com.timife.a_n_nursery_app.vendor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorBttmShtViewModel(vendor: VendorItem, application: Application) :
    AndroidViewModel(application) {
    private val _selectedVendor = MutableLiveData<VendorItem>()
    val selectedVendor: LiveData<VendorItem>
        get() = _selectedVendor

    init {
        _selectedVendor.value = vendor
    }

    private val _navigateToEditVendor = MutableLiveData<VendorItem>()
    val navigateToEditVendor: LiveData<VendorItem>
        get() = _navigateToEditVendor

    fun displayVendorEdit(vendor: VendorItem) {
        _navigateToEditVendor.value = vendor
    }
}