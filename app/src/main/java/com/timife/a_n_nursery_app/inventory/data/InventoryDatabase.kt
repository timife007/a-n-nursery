package com.timife.a_n_nursery_app.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [InventoryItem::class],
    version = 1
)
abstract class InventoryDatabase : RoomDatabase() {
    abstract val getInventoryItemDao: InventoryItemDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: getDatabase(context).also { INSTANCE = it }
        }

        private fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            InventoryDatabase::class.java,
            "InventoryItem.db"
        ).build()
    }
}