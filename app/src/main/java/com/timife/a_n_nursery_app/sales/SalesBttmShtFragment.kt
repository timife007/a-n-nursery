package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R

class SalesBttmShtFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = SalesBttmShtFragment()
    }

    private lateinit var viewModel: SalesBttmShtViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sales_bttm_sht_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SalesBttmShtViewModel::class.java)
        // TODO: Use the ViewModel
    }

}