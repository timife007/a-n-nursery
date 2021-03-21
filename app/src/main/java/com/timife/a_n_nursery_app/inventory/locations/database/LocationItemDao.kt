package com.timife.a_n_nursery_app.inventory.locations.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem

@Dao
interface LocationItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(item: LocationItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateAll(item:List<LocationItem>)

    @Delete
    suspend fun delete(item: LocationItem)

    @Query("SELECT * from location_items")
    fun getAllCategoryItems(): LiveData<List<LocationItem>>
}