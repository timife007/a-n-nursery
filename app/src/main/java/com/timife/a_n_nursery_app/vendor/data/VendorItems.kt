package com.timife.a_n_nursery_app.vendor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vendor_items")
data class VendorItems(
        @ColumnInfo(name = "itemName")
        var vendorName: String,
        @ColumnInfo(name = "itemlastName")
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