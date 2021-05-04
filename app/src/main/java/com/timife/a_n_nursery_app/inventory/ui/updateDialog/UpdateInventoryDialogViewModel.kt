package com.timife.a_n_nursery_app.inventory.ui.updateDialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import kotlinx.coroutines.launch

class UpdateInventoryDialogViewModel(
    product: Inventory,
    application: Application,
    private val inventoryRepository: InventoryRepository
) :
    AndroidViewModel(application) {
    private val _selectedEdit = MutableLiveData<Inventory>()
    val selectedEdit: LiveData<Inventory>
        get() = _selectedEdit

    private val _updateInventoryItem: MutableLiveData<Resource<Inventory>> = MutableLiveData()
    val updateInventoryItem: LiveData<Resource<Inventory>>
        get() = _updateInventoryItem

    init {
        _selectedEdit.value = product
    }

    fun updateInventoryItem(
        productId: Int,
        productName: String,
        botanicalName: String,
        size: String,
        classification: String,
        color: String,
        price: String,
        cost: String,
        lot: String,
        location: String,
        quantity: Int,
        category: String
    ) = viewModelScope.launch {
        _updateInventoryItem.value = Resource.Loading
        _updateInventoryItem.value = inventoryRepository.updateInventoryProduct(
            productId,
            productName,
            botanicalName,
            size,
            classification,
            color,
            price,
            cost,
            lot,
            location,
            quantity,
            category
        )
    }
}