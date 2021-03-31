package com.timife.a_n_nursery_app.inventory.lots.ui.editLots

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository

class EditLotViewModelFactory (private val lot: Lot, private val application: Application,private val lotRepository: LotRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLotViewModel::class.java)) {
            return EditLotViewModel(lot, application,lotRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}