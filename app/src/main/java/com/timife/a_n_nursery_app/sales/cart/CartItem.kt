package com.timife.a_n_nursery_app.sales.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    @ColumnInfo(name="product_name")
    var name:String,
    @ColumnInfo(name="product_size")
    var size:String,
    @ColumnInfo(name="product_quantity")
    var quantity:Int,
    @ColumnInfo(name="product_amount")
    var price:String
){

}