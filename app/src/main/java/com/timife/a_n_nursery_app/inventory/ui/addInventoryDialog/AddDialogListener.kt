package com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog


interface AddDialogListener {
    fun onAddButtonClicked(productName:String,botanicalName:String,size:String,
                           classification:Int, color:String,price:String,cost:String,lot:Int,
                           location:Int,quantity:Int,category:Int)
}

