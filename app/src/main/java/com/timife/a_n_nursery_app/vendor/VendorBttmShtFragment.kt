package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentVendorBttmShtBinding
import com.timife.a_n_nursery_app.inventory.ui.bottomsheet.InvntBttmShtFragmentDirections
import com.timife.a_n_nursery_app.inventory.ui.bottomsheet.InvntBttmShtViewModel

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

        val viewModel = ViewModelProvider(this,viewModelFactory).get(VendorBttmShtViewModel::class.java)
        binding.addCartBtn.setOnClickListener {
            viewModel.displayVendorEdit(vendor)

        }

        viewModel.navigateToEditVendor.observe(viewLifecycleOwner, Observer {

            this.findNavController().navigate(
                VendorBttmShtFragmentDirections.actionVendorBttmShtFragmentToUpdateVendorDialog(it)
            )
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}