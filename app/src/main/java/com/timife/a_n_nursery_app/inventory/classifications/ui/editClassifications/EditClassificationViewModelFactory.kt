package com.timife.a_n_nursery_app.inventory.classifications.ui.editClassifications

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository

class EditClassificationViewModelFactory (private val classification: Classification, private val application: Application,private val classificationRepository: ClassificationRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditClassificationViewModel::class.java)) {
            return EditClassificationViewModel(classification, application,classificationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}