package com.timife.a_n_nursery_app.sales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.sales.cart.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SalesBttmShtViewModel(product: Inventory, application: Application,private val repository: SalesRepository) : AndroidViewModel(application) {

    private val _scannedProduct = MutableLiveData<Inventory>()
    val scannedProduct: LiveData<Inventory>
        get() = _scannedProduct

    init {
        _scannedProduct.value = product
    }

    fun upsert(item:CartItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }
}