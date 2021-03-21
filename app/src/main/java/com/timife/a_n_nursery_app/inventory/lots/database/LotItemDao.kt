package com.timife.a_n_nursery_app.inventory.lots.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem

@Dao
interface LotItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(item: LotItem)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateAll(item:List<LotItem>)

    @Delete
    suspend fun delete(item: LotItem)

    @Query("SELECT * from lot_items")
    fun getAllLotItems(): LiveData<List<LotItem>>
}