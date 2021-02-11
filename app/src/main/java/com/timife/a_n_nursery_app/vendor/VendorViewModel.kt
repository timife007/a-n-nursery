package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import com.timife.a_n_nursery_app.inventory.response.Result
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import com.timife.a_n_nursery_app.vendor.response.Vendors
import kotlinx.coroutines.launch
import retrofit2.Response


class VendorViewModel(
    private val vendorRepository: VendorRepository
) : BaseViewModel(vendorRepository) {

    var vendorPage = 1
    var vendorItemResponse: Vendors? = null
    var searchVendorItemResponse: Vendors? = null
    var searchPage = 1


    private val _vendors: MutableLiveData<Resource<Vendors>> = MutableLiveData()
    val vendors: LiveData<Resource<Vendors>>
        get() = _vendors

    private val _search : MutableLiveData<Resource<Vendors>> =MutableLiveData()
    val search:LiveData<Resource<Vendors>>
        get() = _search

    private val _saveVendor: MutableLiveData<Resource<VendorItem>> = MutableLiveData()
    val saveVendor: LiveData<Resource<VendorItem>>
        get() = _saveVendor

    private val _navigateToSelectedVendor = MutableLiveData<VendorItem>()
    val navigateToSelectedVendor: LiveData<VendorItem>
        get() = _navigateToSelectedVendor

    init {
        getVendorItems()
    }

    fun getVendorItems() = viewModelScope.launch {
        _vendors.value = Resource.Loading
        val itemsResult = vendorRepository.getVendors(vendorPage)
        _vendors.value= handleVendorItemResponse(Response.success(itemsResult))
    }

    fun getVendorSearchItems(firstName: String,lastName: String) = viewModelScope.launch {
        _search.value = Resource.Loading
        val itemsSearchResult = vendorRepository.getSearchVendors(firstName,lastName,searchPage)
        _search.value  =handleSearchItemResponse(Response.success(itemsSearchResult))
    }

    private fun handleVendorItemResponse(response: Response<Vendors>): Resource<Vendors> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                vendorPage++
                if (vendorItemResponse == null){
                    vendorItemResponse =resultResponse
                }else{
                    val oldVendors = vendorItemResponse?.results
                    val newVendors = resultResponse.results
                    oldVendors?.addAll(newVendors)
                }
                return Resource.Success(vendorItemResponse ?: resultResponse)
            }
        }
        return Resource.Failure(true,401,response.errorBody())
    }

    private fun handleSearchItemResponse(response: Response<Vendors>): Resource<Vendors> {
        if(response.isSuccessful){
            response.body()?.let {resultSearchResponse ->
                searchPage++
                if (searchVendorItemResponse == null){
                    searchVendorItemResponse =resultSearchResponse
                }else{
                    val oldVendorSearch = searchVendorItemResponse?.results
                    val newVendorSearch = resultSearchResponse.results
                    oldVendorSearch?.addAll(newVendorSearch)
                }
                return Resource.Success(searchVendorItemResponse ?: resultSearchResponse)
            }
        }
        return Resource.Failure(true,401,response.errorBody())
    }

    fun saveVendorItem(firstName: String,
        lastName: String,
        email: String,
        company: String,
        type: String,
        phoneNumber: String
    ) = viewModelScope.launch {
        _saveVendor.value = Resource.Loading
        _saveVendor.value = vendorRepository.saveVendor(firstName, lastName, email, company, type, phoneNumber)
    }

    fun displayVendorDetails(vendor: VendorItem) {
        _navigateToSelectedVendor.value = vendor
    }

    fun displayVendorDetailsComplete() {
        _navigateToSelectedVendor.value = null
    }
}