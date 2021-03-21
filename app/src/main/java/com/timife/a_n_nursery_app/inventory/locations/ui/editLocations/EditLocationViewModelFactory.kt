package com.timife.a_n_nursery_app.inventory.locations.ui.editLocations

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.locations.network.Location

class EditLocationViewModelFactory (private val location: Location, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLocationViewModel::class.java)) {
            return EditLocationViewModel(location, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}