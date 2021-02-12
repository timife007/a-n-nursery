package com.timife.a_n_nursery_app.inventory.ui


interface AddDialogListener {
    fun onAddButtonClicked(productName:String,botanicalName:String,size:String,classification:String,color:String,price:String,cost:String,lot:String,location:String,quantity:String,category:String)
}