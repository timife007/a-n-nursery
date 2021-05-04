package com.timife.a_n_nursery_app.sales.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding


    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = CartDatabase.invoke(requireContext())
        val cartRepository = CartRepository(database)
        val factory = CartViewModelFactory(cartRepository)
        val viewModel = ViewModelProvider(this,factory).get(CartViewModel::class.java)

        val adapter = CartAdapter(listOf(),viewModel)
        binding.cartRecycler.adapter = adapter
        viewModel.getAllCartItems().observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}