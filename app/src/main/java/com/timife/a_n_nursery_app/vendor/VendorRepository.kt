package com.timife.a_n_nursery_app.vendor

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.vendor.network.VendorsApi

class VendorRepository(private val api: VendorsApi): BaseRepository(){
    suspend fun getVendors(pageNumber: Int) = safeApiCall {
        api.getVendors(pageNumber).results
    }

}