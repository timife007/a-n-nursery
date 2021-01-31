package com.timife.a_n_nursery_app.vendor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentVendorBinding
import com.timife.a_n_nursery_app.inventory.AddDialogListener
import com.timife.a_n_nursery_app.inventory.data.InventoryItem
import com.timife.a_n_nursery_app.inventory.ui.AddInvItemDialog
import com.timife.a_n_nursery_app.inventory.ui.InventAdapter
import com.timife.a_n_nursery_app.inventory.ui.bindRecyclerView
import com.timife.a_n_nursery_app.inventory.ui.bindVenRecyclerView
import com.timife.a_n_nursery_app.login.ui.visible
import com.timife.a_n_nursery_app.vendor.data.VendorItems
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.android.synthetic.main.fragment_inventory.recycler_view
import kotlinx.android.synthetic.main.fragment_vendor.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class VendorFragment : BaseFragment<VendorViewModel, FragmentVendorBinding, VendorRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.venProgress.visible(false)

        val adapter = VendorAdapter(VendorAdapter.OnClickListener {
            //viewModel.displayProductDetails(it)
        })

        viewModel.vendors.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    //adapter.item = it.value
                    binding.venProgress.visible(false)
                    bindVenRecyclerView(recycler_view, it.value)
                   // adapter.notifyDataSetChanged()

                    Toast.makeText(activity, "Vendors: ${it.value}", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    //    binding.inventoryProgress.visible(false)
                    Toast.makeText(requireContext(), "Error loading vendors ${it.errorBody}", Toast.LENGTH_LONG)
                            .show()
                }
                is Resource.Loading -> {
                    binding.venProgress.visible(true)

                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG)
                            .show()

                    //  binding.inventoryProgress.visible(true)
                }

            }
        })

        binding.venAdd.setOnClickListener {
            val dialogFragment = AddVenItemDialog(object : AddVenDialogListener {

                override fun onAddVenButtonClicked(item: VendorItems) {
                    Toast.makeText(
                            requireContext(),
                            "Added to the vendor database",
                            Toast.LENGTH_SHORT
                    ).show()

                }
            })
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }

    }


    override fun getViewModel() = VendorViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentVendorBinding.inflate(inflater, container, false)


    override fun getRepository(): VendorRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(VendorsApi::class.java, token)
        return VendorRepository(api)
    }

}