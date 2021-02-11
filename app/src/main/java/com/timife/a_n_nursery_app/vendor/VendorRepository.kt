package com.timife.a_n_nursery_app.vendor

import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.login.response.LoginResponse
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorRepository(private val api: VendorsApi): BaseRepository(){
    suspend fun getVendors(pageNumber: Int) =
        api.getVendors(pageNumber)


    suspend fun getSearchVendors(firstName: String,lastName:String,pageNumber: Int)=
        api.getSearchVendors(firstName,lastName,pageNumber)


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
}