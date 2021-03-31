package com.timife.a_n_nursery_app.inventory.classifications.ui.editClassifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository
import kotlinx.coroutines.launch

class EditClassificationViewModel (classification: Classification, application: Application,private val classificationRepository: ClassificationRepository) :
    AndroidViewModel(application) {
    private val _selectedEditClassification= MutableLiveData<Classification>()
    val selectedEditClassification: LiveData<Classification>
        get() = _selectedEditClassification

    private val _updateClassification = MutableLiveData<com.timife.a_n_nursery_app.Resource<Classification>>()
    val updateClassification : LiveData<com.timife.a_n_nursery_app.Resource<Classification>>
        get() = _updateClassification

    init {
        _selectedEditClassification.value = classification
    }


    fun updateClassification(classificationId: Int,classificationName: String)= viewModelScope.launch {
        _updateClassification.value = Resource.Loading
        _updateClassification.value = classificationRepository.updateClassification(classificationId, classificationName)
    }
}