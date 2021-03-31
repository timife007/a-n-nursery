package com.timife.a_n_nursery_app.inventory.locations.ui.editLocations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import kotlinx.coroutines.launch

class EditLocationViewModel(location: Location, application: Application,private val locationRepository:LocationRepository) :
    AndroidViewModel(application) {
    private val _selectedEditLocation = MutableLiveData<Location>()
    val selectedEditLocation: LiveData<Location>
        get() = _selectedEditLocation

    private val _updateLocation = MutableLiveData<Resource<Location>>()
    val updateLocation : LiveData<Resource<Location>>
        get() = _updateLocation


    init {
        _selectedEditLocation.value = location
    }


    fun updateLocation(locationId: Int,locationName: String)= viewModelScope.launch {
        _updateLocation.value = Resource.Loading
        _updateLocation.value = locationRepository.updateLocation(locationId, locationName)
    }
}