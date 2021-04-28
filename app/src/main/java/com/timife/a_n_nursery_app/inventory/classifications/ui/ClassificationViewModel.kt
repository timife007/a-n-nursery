package com.timife.a_n_nursery_app.inventory.classifications.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationItems
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import kotlinx.coroutines.launch

class ClassificationViewModel (private val classificationRepository: ClassificationRepository): BaseViewModel(classificationRepository) {
    // TODO: Implement the ViewModel
    private val _classification: MutableLiveData<Resource<ClassificationItems>> = MutableLiveData()
    val classification: LiveData<Resource<ClassificationItems>>
        get() = _classification

    private val _saveClassification: MutableLiveData<Resource<Classification>> = MutableLiveData()
    val saveClassification: LiveData<Resource<Classification>>
        get() = _saveClassification


    private val _navigateToEditClassification = MutableLiveData<Classification>()
    val navigateToEditClassification: LiveData<Classification>
        get() = _navigateToEditClassification



    fun getClassificationItems() = viewModelScope.launch {
        _classification.value = classificationRepository.getClassifications()
    }

    fun deleteClassification(classificationId:Int) = viewModelScope.launch {
        classificationRepository.deleteClassification(classificationId)
    }


    fun saveClassificationItem(classificationName: String) = viewModelScope.launch {
        _saveClassification.value = Resource.Loading
        _saveClassification.value = classificationRepository.saveClassification(classificationName)
    }

    fun displayEditClassification(classification: Classification)  {
        _navigateToEditClassification.value = classification
    }

    fun displayClassificationEditComplete() {
        _navigateToEditClassification.value = null
    }
}