package com.timife.a_n_nursery_app.inventory.lots.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.lots.network.LotApi

class LotRepository (private val api: LotApi): BaseRepository() {
    suspend fun getLot() = safeApiCall {
        api.getLots()
    }

    suspend fun saveLot(lotName: String) = safeApiCall {
        api.saveLot(lotName)
    }
}