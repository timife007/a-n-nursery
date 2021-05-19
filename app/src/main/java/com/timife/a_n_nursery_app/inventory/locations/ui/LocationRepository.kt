package com.timife.a_n_nursery_app.inventory.locations.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi

class LocationRepository(private val api: LocationApi) : BaseRepository() {
    suspend fun getLocation() = safeApiCall {
        api.getLocations()
    }

    suspend fun saveLocation(locationName: String) = safeApiCall {
        api.saveLocation(locationName)
    }

    suspend fun updateLocation(locationId: Int, locationName: String) = safeApiCall {
        api.updateLocation(locationId, locationName)
    }

    suspend fun deleteLocation(locationId: Int) = safeApiCall {
        api.deleteLocation(locationId)
    }
}