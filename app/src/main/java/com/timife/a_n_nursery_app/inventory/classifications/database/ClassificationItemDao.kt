package com.timife.a_n_nursery_app.inventory.classifications.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem

@Dao
interface ClassificationItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(item: ClassificationItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateAll(item:List<ClassificationItem>)


    @Delete
    suspend fun delete(item: ClassificationItem)

    @Query("SELECT * from classification_items")
    fun getAllClassificationItems(): LiveData<List<ClassificationItem>>
}