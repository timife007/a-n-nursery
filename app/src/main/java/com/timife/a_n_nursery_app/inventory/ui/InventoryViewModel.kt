package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationItems
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem
import com.timife.a_n_nursery_app.inventory.locations.network.LocationItems
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.lots.network.LotItems
import com.timife.a_n_nursery_app.inventory.response.Inventory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//private const val default_page =1
class InventoryViewModel(
    private val inventoryRepository: InventoryRepository
) : BaseViewModel(inventoryRepository) {

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val DEFAULT_FILTER = ""
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


    fun getSearchInventory(searchQuery: String) {
        currentQuery.value = searchQuery
    }

    fun getFilterItems(filterQuery: String) {
        currentFilter.value = filterQuery
    }

    private val _category: MutableLiveData<Resource<CategoryItems>> = MutableLiveData()
    val category: LiveData<Resource<CategoryItems>>
        get() = _category

    private val _location: MutableLiveData<Resource<LocationItems>> = MutableLiveData()
    val location: LiveData<Resource<LocationItems>>
        get() = _location

    private val _classification: MutableLiveData<Resource<ClassificationItems>> = MutableLiveData()
    val classification: LiveData<Resource<ClassificationItems>>
        get() = _classification

    private val _lots: MutableLiveData<Resource<LotItems>> = MutableLiveData()
    val lots: LiveData<Resource<LotItems>>
        get() = _lots


    private val _navigateToSelectedProduct = MutableLiveData<Inventory>()
    val navigateToSelectedProduct: LiveData<Inventory>
        get() = _navigateToSelectedProduct


    private val _saveInventory: MutableLiveData<Resource<Inventory>> = MutableLiveData()
    val saveInventory: LiveData<Resource<Inventory>>
        get() = _saveInventory


    fun getLocationItems() = viewModelScope.launch {
        _location.value = inventoryRepository.getLocations()
    }

    fun getCategoryItems() = viewModelScope.launch {
        _category.value = inventoryRepository.getCategory()
    }

    fun getClassificationItems() = viewModelScope.launch {
        _classification.value = inventoryRepository.getClassifications()
    }

    fun getLotItems() = viewModelScope.launch {
        _lots.value = inventoryRepository.getlots()
    }

    fun upsert(item: List<CategoryItem>) {
        CoroutineScope(Dispatchers.Main).launch {  //Using .main because room already provides main safety
            inventoryRepository.insertUpdateAll(item)
        }
    }


    fun upsertLot(item: List<LotItem>) {
        CoroutineScope(Dispatchers.Main).launch {  //Using .main because room already provides main safety
            inventoryRepository.insertUpdateAllLots(item)
        }
    }

    fun upsertLocation(item: List<LocationItem>) {
        CoroutineScope(Dispatchers.Main).launch {  //Using .main because room already provides main safety
            inventoryRepository.insertUpdateAllLocations(item)
        }
    }

    fun upsertClassification(item: List<ClassificationItem>) {
        CoroutineScope(Dispatchers.Main).launch {  //Using .main because room already provides main safety
            inventoryRepository.insertUpdateAllClassifications(item)
        }
    }

//    fun delete(item: ShopItem){
//        CoroutineScope(Dispatchers.Main).launch {
//            shopItemRepository.delete(item)
//        }
//    }

    fun saveInventoryItems(
        productName: String,
        botanicalName: String,
        size: String,
        classification: Int,
        color: String,
        price: String,
        cost: String,
        lot: Int,
        location: Int,
        quantity: Int,
        category: Int
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

    fun deleteInventoryItem(productId: Int) = viewModelScope.launch {
        inventoryRepository.deleteInventoryProduct(productId)
    }

    fun displayProductDetails(product: Inventory) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
//        _navigateToSelectedProduct.value = null
    }
}