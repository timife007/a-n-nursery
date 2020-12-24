package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a_n_nursery_app.R

class VendorFragment : Fragment() {

    companion object {
        fun newInstance() = VendorFragment()
    }

    private lateinit var viewModel: VendorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vendor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}