package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import com.timife.a_n_nursery_app.inventory.data.FilterPagingSource
import com.timife.a_n_nursery_app.inventory.data.InventoryPagingSource
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import java.util.ArrayList

class InventoryRepository(private val api: InventoryApi, private val database: CategoryDatabase) :
    BaseRepository() {
    companion object {
        private const val NETWORK_PAGE_SIZE = 14
    }

    suspend fun getCategory() = safeApiCall {
        val categories = api.getCategories()
        val categoriesNameData = ArrayList<CategoryItem>()
        if(categories.results.isNotEmpty()){
            categories.results.forEach { categoryData ->
                categoriesNameData.add(CategoryItem(categoryData.id!!, categoryData.name!!))
            }
            database.getCategoryDao.deleteAll()
            database.getCategoryDao.insertUpdateAll(*categoriesNameData.toTypedArray())
        }
    }

    suspend fun getLocations() = safeApiCall {
        api.getLocations()
    }

    suspend fun getClassifications() = safeApiCall {
        api.getClassification()
    }

    suspend fun getlots() = safeApiCall {
        api.getLots()
    }

    suspend fun deleteInventoryProduct(id: Int) = safeApiCall {
        api.deleteInventoryItems(id)
    }

    suspend fun insertUpdateAll(item: List<CategoryItem>) {
        database.getCategoryDao.insertUpdateAll(*item.toTypedArray())
    }

    fun getDbCategories(): LiveData<List<CategoryItem>> {
        return database.getCategoryDao.getAllCategoryItems()
    }

    suspend fun insertUpdateAllLots(item: List<LotItem>) {
        database.getLotDao.insertUpdateAll(item)
    }

    suspend fun insertUpdateAllLocations(item: List<LocationItem>) {
        database.getLocationDao.insertUpdateAll(item)
    }

    suspend fun insertUpdateAllClassifications(item: List<ClassificationItem>) {
        database.getClassificationDao.insertUpdateAll(item)
    }
//    val getAllCategoryItem : LiveData<List<CategoryItem>> = database?.getCategoryDao!!.getAllCategoryItems()


    suspend fun postInventoryProducts(
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
    ) = safeApiCall {
        api.saveInventoryItem(
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

    suspend fun updateInventoryProduct(
        inventoryId: Int,
        productName: String,
        botanicalName: String,
        size: String,
        classification: String,
        color: String,
        price: String,
        cost: String,
        lot: String,
        location: String,
        quantity: Int,
        category: String
    ) = safeApiCall {
        api.updateInventoryItem(
            inventoryId,
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


    fun getFilterResults(category: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FilterPagingSource(api, category)
            }
        ).liveData

    fun getSearchResults(searchQuery: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                InventoryPagingSource(api, searchQuery)
            }
        ).liveData
}