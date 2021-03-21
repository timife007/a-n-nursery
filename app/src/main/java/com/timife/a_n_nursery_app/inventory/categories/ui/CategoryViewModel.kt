package com.timife.a_n_nursery_app.inventory.categories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.locations.network.LocationItems
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.inventory.response.Result
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import kotlinx.coroutines.launch

class CategoryViewModel (private val categoryRepository: CategoryRepository): BaseViewModel(categoryRepository) {
    // TODO: Implement the ViewModel
    private val _category: MutableLiveData<Resource<CategoryItems>> = MutableLiveData()
    val category: LiveData<Resource<CategoryItems>>
        get() = _category


    private val _saveCategory: MutableLiveData<Resource<Category>> = MutableLiveData()
    val saveCategory: LiveData<Resource<Category>>
        get() = _saveCategory


    private val _navigateToEditCategory = MutableLiveData<Category>()
    val navigateToEditCategory: LiveData<Category>
        get() = _navigateToEditCategory



    fun getCategoryItems() = viewModelScope.launch {
        _category.value = categoryRepository.getCategories()
    }

    fun saveCategoryItem(categoryName: String) = viewModelScope.launch {
        _saveCategory.value = Resource.Loading
        _saveCategory.value = categoryRepository.saveCategory(categoryName)
    }

    fun displayEditCategory(category:Category)  {
        _navigateToEditCategory.value = category
    }

    fun displayProductDetailsComplete() {
        _navigateToEditCategory.value = null
    }
}