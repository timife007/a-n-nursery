package com.timife.a_n_nursery_app.vendor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import kotlinx.coroutines.launch

class UpdateVendorDialogViewModel (vendor:VendorItem, application: Application, private val vendorRepository: VendorRepository) :
AndroidViewModel(application) {
    private val _selectedEdit = MutableLiveData<VendorItem>()
    val selectedEdit: LiveData<VendorItem>
        get() = _selectedEdit

    private val _updateVendorItem: MutableLiveData<Resource<VendorItem>> = MutableLiveData()
    val updateVendorItem: LiveData<Resource<VendorItem>>
        get() = _updateVendorItem

    init {
        _selectedEdit.value = vendor
    }

    fun updateVendorItem(
        vendorId: Int,
        firstName: String,
        lastName: String,
        email: String,
        company: String,
        type: String,
        phoneNumber: String
    ) = viewModelScope.launch {
        _updateVendorItem.value = Resource.Loading
        _updateVendorItem.value = vendorRepository.updateVendor(
            vendorId, firstName, lastName, email, company, type, phoneNumber
        )
    }
}