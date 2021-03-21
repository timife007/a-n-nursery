package com.timife.a_n_nursery_app.inventory.categories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItemDao
import com.timife.a_n_nursery_app.inventory.data.InventoryItem
import com.timife.a_n_nursery_app.inventory.data.InventoryItemDao
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItemDao
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItemDao

@Database(
    entities = [CategoryItem::class, LocationItem::class,LotItem::class, ClassificationItem::class],
    version = 1
)
abstract class CategoryDatabase : RoomDatabase() {
    abstract val getCategoryDao: CategoryItemDao
    abstract val getLotDao : LotItemDao
    abstract val getLocationDao : LocationItemDao
    abstract val getClassificationDao : ClassificationItemDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: getDatabase(context).also { INSTANCE = it }
        }

        private fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CategoryDatabase::class.java,
            "CategoryItem.db"
        ).build()
    }
}