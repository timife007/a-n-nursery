package com.timife.a_n_nursery_app.inventory.categories.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi

class CategoryRepository(private val api: CategoryApi): BaseRepository() {
    suspend fun getCategories() = safeApiCall {
        api.getCategories()
    }

    suspend fun saveCategory(categoryName: String) = safeApiCall {
        api.saveCategories(categoryName)
    }

    suspend fun updateCategory(categoryId: Int,categoryName: String)= safeApiCall{
        api.updateCategory(categoryId, categoryName)
    }
}