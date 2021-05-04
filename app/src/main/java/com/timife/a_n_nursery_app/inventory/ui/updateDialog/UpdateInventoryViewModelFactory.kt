package com.timife.a_n_nursery_app.inventory.ui.updateDialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository

class UpdateInventoryViewModelFactory(
    private val product: Inventory,
    private val application: Application,
    private val inventoryRepository: InventoryRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateInventoryDialogViewModel::class.java)) {
            return UpdateInventoryDialogViewModel(product, application, inventoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
