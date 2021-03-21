package com.timife.a_n_nursery_app.inventory.ui.updateDialog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timife.a_n_nursery_app.inventory.response.Result

class UpdateInventoryDialogViewModel (product:Result, application: Application) :
    AndroidViewModel(application) {
    private val _selectedEdit = MutableLiveData<Result>()
    val selectedEdit: LiveData<Result>
        get() = _selectedEdit

    init {
        _selectedEdit.value = product
    }
}