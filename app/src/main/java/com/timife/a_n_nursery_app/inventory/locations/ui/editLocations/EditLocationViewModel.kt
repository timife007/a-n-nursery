package com.timife.a_n_nursery_app.inventory.locations.ui.editLocations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.locations.network.Location

class EditLocationViewModel(location: Location, application: Application) :
    AndroidViewModel(application) {
    private val _selectedEditLocation = MutableLiveData<Location>()
    val selectedEditLocation: LiveData<Location>
        get() = _selectedEditLocation

    init {
        _selectedEditLocation.value = location
    }
}