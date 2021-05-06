package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import kotlinx.coroutines.launch

class SalesViewModel(private val salesRepository: SalesRepository) :
    BaseViewModel(salesRepository) {
    // TODO: Implement the ViewModel

    private val _barcodeItem = LiveEvent<Resource<InventoryItems>>()
    val barcodeItem: LiveEvent<Resource<InventoryItems>>
        get() = _barcodeItem

    private val _navigateToScannedItem = MutableLiveData<Inventory>()
    val navigateToScannedItem: LiveData<Inventory>
        get() = _navigateToScannedItem


    fun searchByBarcode(barcode_digit: String) = viewModelScope.launch {
        _barcodeItem.value = salesRepository.searchByBarcode(barcode_digit)
    }

    fun displayScannedItem(inventory: Inventory) {
        _navigateToScannedItem.value = inventory
    }
}