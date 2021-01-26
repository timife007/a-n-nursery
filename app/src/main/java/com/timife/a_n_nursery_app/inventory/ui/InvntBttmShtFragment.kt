package com.timife.a_n_nursery_app.inventory.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.databinding.FragmentInventBttmShtBinding

class InvntBttmShtFragment : BottomSheetDialogFragment() {
        private lateinit var binding : FragmentInventBttmShtBinding

    private lateinit var viewModel: InvntBttmShtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventBttmShtBinding.inflate(inflater)
        return binding.root
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InvntBttmShtViewModel::class.java)
        binding.addCartBtn.setOnClickListener {
            Toast.makeText(requireContext(),"This is an add to cart button",Toast.LENGTH_LONG).show()
        }
    }
}