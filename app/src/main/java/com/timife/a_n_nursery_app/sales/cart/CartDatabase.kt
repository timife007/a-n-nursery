package com.timife.a_n_nursery_app.sales.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [CartItem::class],
    version = 1
)
abstract class CartDatabase: RoomDatabase() {
    abstract fun cartDao():CartDao

    companion object{
        @Volatile
        private var instance: CartDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) =  instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
            CartDatabase::class.java,"CartDB.db").build()
    }
}