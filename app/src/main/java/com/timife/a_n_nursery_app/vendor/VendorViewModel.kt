package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import com.timife.a_n_nursery_app.inventory.response.Result
import com.timife.a_n_nursery_app.inventory.ui.InventoryViewModel
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import com.timife.a_n_nursery_app.vendor.response.Vendors
import kotlinx.coroutines.launch
import retrofit2.Response


class VendorViewModel(
    private val vendorRepository: VendorRepository
) : BaseViewModel(vendorRepository) {
    companion object {
        private const val DEFAULT_VENDOR_QUERY = ""
    }
    private val currentVendorQuery = MutableLiveData(
        DEFAULT_VENDOR_QUERY
    )
    val searchVendor = currentVendorQuery.switchMap { queryString ->
        vendorRepository.getSearchVendorsResults(queryString).cachedIn(viewModelScope)
    }


    private val _saveVendor: MutableLiveData<Resource<VendorItem>> = MutableLiveData()
    val saveVendor: LiveData<Resource<VendorItem>>
        get() = _saveVendor

    private val _navigateToSelectedVendor = MutableLiveData<VendorItem>()
    val navigateToSelectedVendor: LiveData<VendorItem>
        get() = _navigateToSelectedVendor



    fun getVendorSearchItems(firstName: String) {
       currentVendorQuery.value = firstName
    }

    fun saveVendorItem(firstName: String,
        lastName: String,
        email: String,
        company: String,
        type: String,
        phoneNumber: String
    ) = viewModelScope.launch {
        _saveVendor.value = Resource.Loading
        _saveVendor.value = vendorRepository.saveVendor(firstName, lastName, email, company, type, phoneNumber)
    }

    fun displayVendorDetails(vendor: VendorItem) {
        _navigateToSelectedVendor.value = vendor
    }

    fun displayVendorDetailsComplete() {
        _navigateToSelectedVendor.value = null
    }
}