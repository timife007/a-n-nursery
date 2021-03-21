package com.timife.a_n_nursery_app.inventory.ui.updateDialog

interface UpdateDialogListener {
    fun onUpdateButtonClicked(productName: String,botanicalName: String,size: String,classification: String,color: String,price: String,cost: String,lot: String,location: String,quantity: String,category: String)
}