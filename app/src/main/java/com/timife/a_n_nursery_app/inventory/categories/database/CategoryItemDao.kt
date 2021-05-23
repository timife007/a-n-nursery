package com.timife.a_n_nursery_app.inventory.categories.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.timife.a_n_nursery_app.inventory.data.InventoryItem

@Dao
interface CategoryItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(item: CategoryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateAll(vararg item: CategoryItem)

    @Delete
    suspend fun delete(item: CategoryItem)

    @Query("DELETE FROM category_items")
    suspend fun deleteAll()

    @Query("SELECT * from category_items")
    fun getAllCategoryItems(): LiveData<List<CategoryItem>>
}