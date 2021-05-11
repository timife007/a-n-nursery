package com.timife.a_n_nursery_app.sales.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.databinding.FragmentCartBinding
import java.text.NumberFormat
import java.util.*

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding


    private lateinit var viewModel: CartViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        val database = CartDatabase.invoke(requireContext())
        val cartRepository = CartRepository(database)
        val factory = CartViewModelFactory(cartRepository)
        val viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        val adapter = CartAdapter(listOf(), viewModel)
        binding.cartRecycler.adapter = adapter
        var totalAmount: Int = 0;
        var totalQuantity: Int = 0;
        viewModel.getAllCartItems().observe(viewLifecycleOwner, Observer {
            totalAmount = it.map { cartItem -> cartItem.price.replace(".00", "").toInt() }.sum()
            totalQuantity = it.map { cartItem -> cartItem.quantity }.sum()
            binding.amountText.text =
                NumberFormat.getNumberInstance(Locale.US).format(totalAmount) + ".00"
            binding.quantityText.text = totalQuantity.toString()
            binding.totalText.text =
                NumberFormat.getNumberInstance(Locale.US)
                    .format(totalAmount * totalQuantity) + ".00"
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }
}