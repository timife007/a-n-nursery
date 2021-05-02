package com.timife.a_n_nursery_app.sales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timife.a_n_nursery_app.inventory.response.Inventory

class SalesBttmShtViewModel(product: Inventory, application: Application) : AndroidViewModel(application) {

    private val _scannedProduct = MutableLiveData<Inventory>()
    val scannedProduct: LiveData<Inventory>
        get() = _scannedProduct

    init {
        _scannedProduct.value = product
    }
    // TODO: Implement the ViewModel


}