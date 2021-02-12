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
) : BaseViewModel(inventoryRepository) {

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val  DEFAULT_FILTER = ""
    }

    private val currentFilter = MutableLiveData(
        DEFAULT_FILTER
    )
    private val currentQuery = MutableLiveData(
        DEFAULT_QUERY
    )
    val result = currentQuery.switchMap { queryString ->
        inventoryRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    val filter = currentFilter.switchMap { categoryString ->
       inventoryRepository.getFilterResults(categoryString).cachedIn(viewModelScope)
}

    fun getSearchInventory (searchQuery: String){
        currentQuery.value = searchQuery
    }

    fun getFilterItems(filterQuery: String) {
        currentFilter.value = filterQuery
    }


    private val _navigateToSelectedProduct = MutableLiveData<Result>()
    val navigateToSelectedProduct: LiveData<Result>
        get() = _navigateToSelectedProduct

    private val _saveInventory: MutableLiveData<Resource<Result>> = MutableLiveData()
    val saveInventory: LiveData<Resource<Result>>
        get() = _saveInventory



    fun saveInventoryItems(
        productName: String,
        botanicalName: String,
        size: String,
        classification: String,
        color: String,
        price: String,
        cost: String,
        lot: String,
        location: String,
        quantity: String,
        category: String
    ) = viewModelScope.launch {
        _saveInventory.value = Resource.Loading
        _saveInventory.value = inventoryRepository.postInventoryProducts(
            productName,
            botanicalName,
            size,
            classification,
            color,
            price,
            cost,
            lot,
            location,
            quantity,
            category
        )
    }

    fun displayProductDetails(product: Result) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
}