package com.timife.a_n_nursery_app.sales

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.cart.CartItem
import com.timife.a_n_nursery_app.sales.network.SalesApi

class SalesRepository(private val api: SalesApi,private val database: CartDatabase) : BaseRepository() {


    suspend fun searchByBarcode(barcode_digit: String) = safeApiCall {
        api.getProductByBarcode(barcode_digit)
    }

    suspend fun upsert(item:CartItem) = database.cartDao.upsertCartItem(item)

}