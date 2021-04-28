package com.timife.a_n_nursery_app.vendor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.inventory.ui.updateDialog.UpdateInventoryDialogViewModel
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class UpdateVendorViewModelFactory(private val vendor: VendorItem, private val application: Application, private val vendorRepository: VendorRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateVendorDialogViewModel::class.java)) {
            return UpdateVendorDialogViewModel(vendor, application,vendorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}