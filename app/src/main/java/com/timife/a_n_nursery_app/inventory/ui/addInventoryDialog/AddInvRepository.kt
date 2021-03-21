package com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog

import androidx.lifecycle.LiveData
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem

class AddInvRepository(private val database: CategoryDatabase) {

    val getAllCategoryItems : LiveData<List<CategoryItem>> = database.getCategoryDao.getAllCategoryItems()

    val getAllLotItems : LiveData<List<LotItem>> = database.getLotDao.getAllLotItems()

    val getAllLocationItems : LiveData<List<LocationItem>> = database.getLocationDao.getAllCategoryItems()

    val getAllClassificationItems : LiveData<List<ClassificationItem>> = database.getClassificationDao.getAllClassificationItems()



}