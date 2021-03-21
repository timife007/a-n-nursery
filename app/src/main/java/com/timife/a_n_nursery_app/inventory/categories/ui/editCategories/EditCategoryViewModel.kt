package com.timife.a_n_nursery_app.inventory.categories.ui.editCategories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.locations.network.Location

class EditCategoryViewModel (category: Category, application: Application) :
    AndroidViewModel(application) {
    private val _selectedEditCategory = MutableLiveData<Category>()
    val selectedEditCategory: LiveData<Category>
        get() = _selectedEditCategory

    init {
        _selectedEditCategory.value = category
    }
}