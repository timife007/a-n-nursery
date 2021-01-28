package com.timife.a_n_nursery_app.inventory.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentInventBttmShtBinding

class InvntBttmShtFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentInventBttmShtBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding = FragmentInventBttmShtBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = InvntBttmShtFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = InvntBttmShtViewModelFactory(product, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(InvntBttmShtViewModel::class.java)
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.addCartBtn.setOnClickListener {
            Toast.makeText(requireContext(), "This is an add to cart button", Toast.LENGTH_LONG)
                .show()
        }
    }
}