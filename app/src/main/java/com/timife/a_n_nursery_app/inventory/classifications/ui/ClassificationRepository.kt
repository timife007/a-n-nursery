package com.timife.a_n_nursery_app.inventory.classifications.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationApi

class ClassificationRepository (private val api: ClassificationApi): BaseRepository() {
    suspend fun getClassifications() = safeApiCall {
        api.getClassifications()
    }

    suspend fun saveClassification(classificationName: String) = safeApiCall {
        api.saveClassification(classificationName)
    }


    suspend fun updateClassification(classificationId: Int,classificationName: String)= safeApiCall{
        api.updateClassification(classificationId, classificationName)
    }
}