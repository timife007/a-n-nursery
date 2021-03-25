package com.timife.a_n_nursery_app.vendor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.InventoryPagingSource
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.login.response.LoginResponse
import com.timife.a_n_nursery_app.vendor.network.VendorPagingSource
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorRepository(private val api: VendorsApi): BaseRepository(){

    companion object {
        private const val NETWORK_PAGE_SIZE = 14
    }

    fun getSearchVendorsResults(firstNameSearch: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                VendorPagingSource(api, firstNameSearch)
            }
        ).liveData

    suspend fun saveVendor(
        firstName: String,
        lastName: String,
        email: String,
        company: String,
        type: String,
        phoneNumber:String
    ): Resource<VendorItem> {
        return safeApiCall {
            api.postVendors(firstName, lastName, email, company, type, phoneNumber)
        }
    }
    suspend fun getVendors(

    ) {
    }

}