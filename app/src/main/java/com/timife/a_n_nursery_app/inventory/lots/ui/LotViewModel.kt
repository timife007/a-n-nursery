package com.timife.a_n_nursery_app.inventory.lots.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.network.LocationItems
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import com.timife.a_n_nursery_app.inventory.lots.network.LotItems
import kotlinx.coroutines.launch

class LotViewModel (private val lotRepository: LotRepository): BaseViewModel(lotRepository) {
    // TODO: Implement the ViewModel
    private val _lot: MutableLiveData<Resource<LotItems>> = MutableLiveData()
    val lot: LiveData<Resource<LotItems>>
        get() = _lot


    private val _saveLot: MutableLiveData<Resource<Lot>> = MutableLiveData()
    val saveLot: LiveData<Resource<Lot>>
        get() = _saveLot

    private val _navigateToEditLot = MutableLiveData<Lot>()
    val navigateToEditLot: LiveData<Lot>
        get() = _navigateToEditLot


    fun getLotItems() = viewModelScope.launch {
        _lot.value = lotRepository.getLot()
    }
    fun deleteLotItem(lotId:Int) =  viewModelScope.launch {
        lotRepository.deleteLot(lotId)
    }


    fun saveLotName(lotName: String) = viewModelScope.launch {
        _saveLot.value = Resource.Loading
        _saveLot.value = lotRepository.saveLot(lotName)
    }

    fun displayEditLot(lot: Lot)  {
        _navigateToEditLot.value = lot
    }

    fun displayLotEditComplete() {
        _navigateToEditLot.value = null
    }
}