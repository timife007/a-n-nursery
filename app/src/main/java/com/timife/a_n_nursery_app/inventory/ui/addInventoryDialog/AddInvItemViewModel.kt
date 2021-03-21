package com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog

import androidx.lifecycle.ViewModel

class AddInvItemViewModel(
    private val addInvItemRepository: AddInvRepository
) :ViewModel(){
    val getAllCategoryItems = addInvItemRepository.getAllCategoryItems

    val getAllLotItems = addInvItemRepository.getAllLotItems

    val getAllLocationItems = addInvItemRepository.getAllLocationItems

    val getAllClassificationItems = addInvItemRepository.getAllClassificationItems

}