package com.timife.a_n_nursery_app.inventory.categories.ui.editCategories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository

class EditCategoryViewModelFactory (private val category: Category, private val application: Application,private val categoryRepository: CategoryRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditCategoryViewModel::class.java)) {
            return EditCategoryViewModel(category, application,categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}