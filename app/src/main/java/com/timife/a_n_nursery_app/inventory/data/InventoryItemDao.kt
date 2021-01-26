package com.timife.a_n_nursery_app.inventory.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InventoryItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: InventoryItem)

    @Delete
    suspend fun delete(item: InventoryItem)

    @Query("SELECT * from inventory_items")
    fun getAllInventoryItems(): LiveData<List<InventoryItem>>
}