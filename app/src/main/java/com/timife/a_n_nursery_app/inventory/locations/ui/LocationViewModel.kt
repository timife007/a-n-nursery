package com.timife.a_n_nursery_app.inventory.locations.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.network.LocationItems
import kotlinx.coroutines.launch

class LocationViewModel (private val locationRepository: LocationRepository): BaseViewModel(locationRepository) {
    // TODO: Implement the ViewModel
    private val _location: MutableLiveData<Resource<LocationItems>> = MutableLiveData()
    val location: LiveData<Resource<LocationItems>>
        get() = _location


    private val _saveLocation: MutableLiveData<Resource<Location>> = MutableLiveData()
    val saveLocation: LiveData<Resource<Location>>
        get() = _saveLocation

    private val _navigateToEditLocation = MutableLiveData<Location>()
    val navigateToEditLocation: LiveData<Location>
        get() = _navigateToEditLocation



    fun getLocationItems() = viewModelScope.launch {
        _location.value = locationRepository.getLocation()
    }

    fun deleteLocationItem(locationId:Int) = viewModelScope.launch {
        locationRepository.deleteLocation(locationId)
    }

    fun saveLocationName(locationName: String) = viewModelScope.launch {
        _saveLocation.value = Resource.Loading
        _saveLocation.value = locationRepository.saveLocation(locationName)
    }

    fun displayEditLocation(location: Location)  {
        _navigateToEditLocation.value = location
    }

    fun displayLocationEditComplete() {
        _navigateToEditLocation.value = null
    }
}