package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.Result
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val inventoryRepository: InventoryRepository
) : BaseViewModel(inventoryRepository) {

    var inventoryPage = 1

    private val _products: MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    val products: LiveData<Resource<List<Result>>>
        get() = _products

    private val _navigateToSelectedProduct = MutableLiveData<Result>()
    val navigateToSelectedProduct: LiveData<Result>
        get() = _navigateToSelectedProduct

    private val _search: MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    var searchPage = 1

    //    init {
//    getInventoryItems()
//}
    fun getInventoryItems() = viewModelScope.launch {
        _products.value = Resource.Loading
        val itemsResult = inventoryRepository.getInventoryProducts(inventoryPage)
        _products.value = itemsResult
    }

    fun getSearchItems(searchQuery: String) = viewModelScope.launch {
        _products.value = Resource.Loading
        val searchResult = inventoryRepository.getInventorySearch(searchQuery, searchPage)
        _products.value = searchResult
    }

    fun displayProductDetails(product: Result) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
}