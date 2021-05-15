package com.timife.a_n_nursery_app.sales

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.sales.cart.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SalesBttmShtViewModel(
    product: Inventory,
    application: Application,
    private val repository: SalesRepository
) : AndroidViewModel(application) {

    private val _scannedProduct = MutableLiveData<Inventory>()
    val scannedProduct: LiveData<Inventory>
        get() = _scannedProduct

    init {
        _scannedProduct.value = product
    }

    fun upsert(item: CartItem) = CoroutineScope(Dispatchers.IO).launch {
        val cartItem = repository.checkIfItemExists(item.id!!)
        if (cartItem != null) {
            item.quantity = cartItem.quantity + 1
            repository.update(item)
        }else{
            item.quantity = 1
            repository.upsert(item)
        }
    }

    fun getAllCartItems() =
        repository.getAllCartItems()

}