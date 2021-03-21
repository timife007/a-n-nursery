package com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AddInvItemViewModelFactory(
    private val addInvRepository: AddInvRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddInvItemViewModel(addInvRepository) as T
    }
}