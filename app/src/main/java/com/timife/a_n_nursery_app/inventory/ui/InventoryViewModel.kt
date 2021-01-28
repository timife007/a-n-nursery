package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.Result
import kotlinx.coroutines.launch

class InventoryViewModel(
 private val inventoryRepository: InventoryRepository
) : BaseViewModel(inventoryRepository){

    var inventoryPage = 1

    private val _products: MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    val products: LiveData<Resource<List<Result>>>
        get() = _products
    private val _navigateToSelectedProduct = MutableLiveData<Result>()
    val navigateToSelectedProduct: LiveData<Result>
        get() = _navigateToSelectedProduct

//    private val _currentQuery = MutableLiveData(DEFAULT_QUERY)
//
//    companion object {
//        private const val DEFAULT_QUERY = "cats"
//    }
init {
    getInventoryItems()

}
    private fun getInventoryItems() =viewModelScope.launch {
        _products.value= Resource.Loading
        val itemsResult = inventoryRepository.getInventoryProducts(inventoryPage)
        _products.value = itemsResult
    }
//    val inventoryProducts  = _currentQuery.switchMap { queryString->
//        inventoryRepository.getSearchResult(queryString).cachedIn(viewModelScope)
//    }

//    private fun searchInventory(query: String) {
//        _currentQuery.value = query
////    val itemsResult = inventoryRepository.getSearchResult("Yucca")
//}


fun displayProductDetails(product: Result) {
    _navigateToSelectedProduct.value = product
}

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }

}