package com.timife.a_n_nursery_app.inventory.ui.bottomsheet

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.response.Inventory

class InvntBttmShtViewModelFactory(
    private val product: Inventory,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvntBttmShtViewModel::class.java)) {
            return InvntBttmShtViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
