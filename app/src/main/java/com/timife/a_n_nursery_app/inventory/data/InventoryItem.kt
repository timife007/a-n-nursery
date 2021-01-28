package com.timife.a_n_nursery_app.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @ColumnInfo(name = "itemName")
    var productName: String,
    @ColumnInfo(name = "itemVariant")
    var variant: String,
    @ColumnInfo(name = "itemLot")
    var lot: String,
    @ColumnInfo(name = "itemCost")
    var cost: String,
    @ColumnInfo(name = "itemPrice")
    var price: String,
){
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null

}