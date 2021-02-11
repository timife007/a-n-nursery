package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.databinding.FragmentVendorBttmShtBinding

class VendorBttmShtFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentVendorBttmShtBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding = FragmentVendorBttmShtBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val vendor = VendorBttmShtFragmentArgs.fromBundle(requireArguments()).selectedVendor
        val viewModelFactory = VendorBttmShtViewModelFactory(vendor, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(VendorBttmShtViewModel::class.java)
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