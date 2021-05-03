package com.timife.a_n_nursery_app.sales.cart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(item:CartItem)

    @Delete
    suspend fun deleteCartItem(item:CartItem)

    @Query("SELECT * from cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>
}