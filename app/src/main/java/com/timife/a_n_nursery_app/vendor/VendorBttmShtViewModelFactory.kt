package com.timife.a_n_nursery_app.vendor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorBttmShtViewModelFactory(
    private val vendor: VendorItem,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendorBttmShtViewModel::class.java)) {
            return VendorBttmShtViewModel(vendor, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}