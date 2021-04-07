package com.timife.a_n_nursery_app.inventory.ui.bottomsheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.response.Inventory

class InvntBttmShtViewModel(product:Inventory, application: Application) :
    AndroidViewModel(application) {
    private val _selectedProduct = MutableLiveData<Inventory>()
    val selectedProduct: LiveData<Inventory>
        get() = _selectedProduct

    private val _navigateToEditProduct = MutableLiveData<Inventory>()
    val navigateToEditProduct: LiveData<Inventory>
        get() = _navigateToEditProduct

    init {
        _selectedProduct.value = product
    }

    fun displayProductEdit(product: Inventory){
        _navigateToEditProduct.value = product
    }

    fun displayProductEditComplete() {
//        _navigateToSelectedProduct.value = null
    }
}