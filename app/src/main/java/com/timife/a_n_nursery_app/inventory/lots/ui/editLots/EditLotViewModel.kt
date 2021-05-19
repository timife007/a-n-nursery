package com.timife.a_n_nursery_app.inventory.lots.ui.editLots

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository
import kotlinx.coroutines.launch

class EditLotViewModel (lot: Lot, application: Application,private val lotRepository:LotRepository) :
    AndroidViewModel(application) {
    private val _selectedEditLot = MutableLiveData<Lot>()
    val selectedEditLot: LiveData<Lot>
        get() = _selectedEditLot

    private val _updateLot : MutableLiveData<Resource<Lot>> = MutableLiveData()
    val updateLot : LiveData<Resource<Lot>>
    get() = _updateLot

    init {
        _selectedEditLot.value = lot
    }

    fun updateLot(lotId:Int,lotName: String)= viewModelScope.launch {
        _updateLot.value = Resource.Loading
        _updateLot.value = lotRepository.updateLot(lotId, lotName)
    }
}