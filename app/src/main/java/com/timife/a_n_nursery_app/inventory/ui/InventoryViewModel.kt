package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.InventoryProducts
import com.timife.a_n_nursery_app.inventory.response.InventoryProductsItem
import kotlinx.coroutines.launch

class InventoryViewModel(
//    private val inventItemRepository: InventItemRepository
 private val inventoryRepository: InventoryRepository
) : BaseViewModel(inventoryRepository){
    private val _products: MutableLiveData<Resource<ArrayList<InventoryProductsItem>>> = MutableLiveData()
    val products: LiveData<Resource<ArrayList<InventoryProductsItem>>>
        get() = _products


    fun getInventoryItems() =viewModelScope.launch {
        _products.value= Resource.Loading
        _products.value = inventoryRepository.getInventoryProducts()
    }

//    fun upsert(item: InventoryItem){
//        CoroutineScope(Dispatchers.Main).launch {  //Using .main because room already provides main safety
//            inventItemRepository.upsert(item)
//        }
//    }
//    fun delete(item: InventoryItem){
//        CoroutineScope(Dispatchers.Main).launch {
//            inventItemRepository.delete(item)
//        }
//    }
//    val getAllInventoryItems = inventItemRepository.getAllShopItems


}