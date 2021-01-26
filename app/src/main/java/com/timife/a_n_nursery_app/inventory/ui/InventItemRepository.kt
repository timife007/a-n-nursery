package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.LiveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.InventoryDatabase
import com.timife.a_n_nursery_app.inventory.data.InventoryItem

class InventItemRepository(private val db: InventoryDatabase): BaseRepository(){

    suspend fun upsert(item: InventoryItem){
        db.getInventoryItemDao.upsert(item)
    }
    suspend fun delete(item: InventoryItem){
        db.getInventoryItemDao.delete(item)
    }
    val getAllShopItems : LiveData<List<InventoryItem>> = db.getInventoryItemDao.getAllInventoryItems()
}