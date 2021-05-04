package com.timife.a_n_nursery_app.sales

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.sales.cart.CartViewModel

class SalesViewModelFactory(
    private val product: Inventory,
    private val application: Application,
    private val repository:SalesRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesBttmShtViewModel::class.java)) {
            return SalesBttmShtViewModel(product, application,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}