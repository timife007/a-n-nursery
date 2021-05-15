package com.timife.a_n_nursery_app.inventory.categories.ui.editCategories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import kotlinx.coroutines.launch

class EditCategoryViewModel (category: Category,application: Application, private val categoryRepository: CategoryRepository) :
    AndroidViewModel(application) {
    private val _selectedEditCategory = MutableLiveData<Category>()
    val selectedEditCategory: LiveData<Category>
        get() = _selectedEditCategory

    private val _updateCategory = MutableLiveData<Resource<Category>>()
    val updateCategory : LiveData<Resource<Category>>
    get() = _updateCategory

    init {
        _selectedEditCategory.value = category
    }

     fun updateCategory(categoryId: Int,categoryName: String)= viewModelScope.launch {
         _updateCategory.value = Resource.Loading
         _updateCategory.value = categoryRepository.updateCategory(categoryId, categoryName)
    }
}