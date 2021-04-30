package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import kotlinx.coroutines.launch

class SalesViewModel(private val salesRepository: SalesRepository) : BaseViewModel(salesRepository) {
    // TODO: Implement the ViewModel

    private val _barcodeItem: MutableLiveData<Resource<InventoryItems>> = MutableLiveData()
    val barcodeItem: LiveData<Resource<InventoryItems>>
        get() = _barcodeItem


    fun searchByBarcode(barcode_digit:String) = viewModelScope.launch {
       salesRepository.searchByBarcode(barcode_digit)
    }
}