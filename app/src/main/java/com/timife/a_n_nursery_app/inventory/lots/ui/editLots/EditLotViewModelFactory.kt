package com.timife.a_n_nursery_app.inventory.lots.ui.editLots

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.lots.network.Lot

class EditLotViewModelFactory (private val lot: Lot, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLotViewModel::class.java)) {
            return EditLotViewModel(lot, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}