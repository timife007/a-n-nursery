package com.timife.a_n_nursery_app.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.activities.response.Activities
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems

class ActivitiesViewModel(private val activitiesRepository: ActivitiesRepository) : BaseViewModel(activitiesRepository) {
    // TODO: Implement the ViewModel


    val result = activitiesRepository.getActivities().cachedIn(viewModelScope)



}