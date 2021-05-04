package com.timife.a_n_nursery_app.sales.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.R
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartAdapter(
    var items : List<CartItem>,
    private val viewModel: CartViewModel
): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentCartItem = items[position]

        holder.itemView.product_name.text = currentCartItem.name
        holder.itemView.price.text = currentCartItem.price
        holder.itemView.qtity_text.text = currentCartItem.quantity.toString()

        holder.itemView.add.setOnClickListener {
            currentCartItem.quantity++
            viewModel.upsert(currentCartItem)
        }

        holder.itemView.minus.setOnClickListener {
            if(currentCartItem.quantity > 0){
                currentCartItem.quantity--
                viewModel.upsert(currentCartItem)

            }
        }

    }
}