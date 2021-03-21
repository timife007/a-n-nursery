package com.timife.a_n_nursery_app.inventory.classifications.ui.editClassifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification

class EditClassificationViewModel (classification: Classification, application: Application) :
    AndroidViewModel(application) {
    private val _selectedEditClassification= MutableLiveData<Classification>()
    val selectedEditClassification: LiveData<Classification>
        get() = _selectedEditClassification

    init {
        _selectedEditClassification.value = classification
    }
}