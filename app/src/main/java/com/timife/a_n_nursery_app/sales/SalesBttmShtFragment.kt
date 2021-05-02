package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.downloader.PRDownloader
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentInventBttmShtBinding
import com.timife.a_n_nursery_app.databinding.SalesBttmShtFragmentBinding
import com.timife.a_n_nursery_app.inventory.ui.bottomsheet.InvntBttmShtFragmentArgs
import com.timife.a_n_nursery_app.inventory.ui.bottomsheet.InvntBttmShtViewModel
import com.timife.a_n_nursery_app.inventory.ui.bottomsheet.InvntBttmShtViewModelFactory

class SalesBttmShtFragment : BottomSheetDialogFragment() {
    private lateinit var binding: SalesBttmShtFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        binding = SalesBttmShtFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct
        val productPrice = SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct.price
        val viewModelFactory = SalesViewModelFactory(product, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(SalesBttmShtViewModel::class.java)
//        val viewModel = ViewModelProvider(this,viewModelFactory).get(SalesBttmShtViewModel::class.java)
        binding.itemPrice.text = productPrice

        return binding.root
    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }

}