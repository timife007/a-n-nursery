package com.timife.a_n_nursery_app.inventory

import com.timife.a_n_nursery_app.inventory.data.InventoryItem

interface AddDialogListener {
    fun onAddButtonClicked(item: InventoryItem)
}