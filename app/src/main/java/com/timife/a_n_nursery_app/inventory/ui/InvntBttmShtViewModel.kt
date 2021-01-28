package com.timife.a_n_nursery_app.inventory.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.response.Result

class InvntBttmShtViewModel(product: Result, application: Application) :
    AndroidViewModel(application) {
    private val _selectedProduct = MutableLiveData<Result>()
    val selectedProduct: LiveData<Result>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }
}