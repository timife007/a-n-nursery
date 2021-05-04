package com.timife.a_n_nursery_app.sales.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
): ViewModel() {

    fun getAllCartItems()=
    cartRepository.getAllCartItems()

    fun upsert(item:CartItem) = CoroutineScope(Dispatchers.Main).launch {
        cartRepository.upsert(item)
    }
}