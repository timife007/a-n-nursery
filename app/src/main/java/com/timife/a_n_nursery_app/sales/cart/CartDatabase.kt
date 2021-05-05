package com.timife.a_n_nursery_app.sales.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [CartItem::class],
    version = 2
)
abstract class CartDatabase: RoomDatabase() {
    abstract val cartDao:CartDao

    companion object{
        @Volatile
        private var INSTANCE: CartDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =  INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: createDatabase(context).also{ INSTANCE = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
            CartDatabase::class.java,"CartDB.db").build()
    }
}