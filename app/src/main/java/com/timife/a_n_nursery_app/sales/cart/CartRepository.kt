package com.timife.a_n_nursery_app.sales.cart

class CartRepository (
    private val database: CartDatabase
){
    fun getAllCartItems() = database.cartDao.getAllCartItems()
}