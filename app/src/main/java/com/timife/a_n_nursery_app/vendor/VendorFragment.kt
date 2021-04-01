package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentVendorBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import com.timife.a_n_nursery_app.vendor.ui.VendorLoadStateAdapter
import com.timife.a_n_nursery_app.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class VendorFragment : BaseFragment<VendorViewModel, FragmentVendorBinding, VendorRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVendorBinding.bind(view)

        val adapter = VendorAdapter(VendorAdapter.OnClickListener {
            viewModel.displayVendorDetails(it)
        })

        binding.recyclerView.adapter = adapter
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = VendorLoadStateAdapter { adapter.retry() },
                footer = VendorLoadStateAdapter { adapter.retry() }
            )
            venRecyclerRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.searchVendor.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                venProgress.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                venRecyclerRetry.isVisible = loadState.source.refresh is LoadState.Error
                vendorNoResultText.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    vendorQueryNoResultText.isVisible = true

                } else {
                    vendorQueryNoResultText.isVisible = false
                }
            }
        }


        viewModel.navigateToSelectedVendor.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        VendorFragmentDirections.actionVendorFragmentToVendorBttmShtFragment(
                            it
                        )
                    )
                viewModel.displayVendorDetailsComplete()
            }
        })

        viewModel.saveVendor.observe(viewLifecycleOwner, {
            binding.venProgress.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(),"${it.value}",Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading ->{
                    showProgressBar()
                    binding.venProgress.visible(true)
                }
            }
        })


        binding.venAdd.setOnClickListener {
            val dialogFragment = AddVenItemDialog(object : AddVenDialogListener {
                override fun onAddVenButtonClicked(
                    firstName: String,
                    lastName: String,
                    email: String,
                    company: String,
                    type: String,
                    phoneNumber: String
                ) {
                    viewModel.saveVendorItem(firstName, lastName, email, company, type, phoneNumber)
                }
            })
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
        setHasOptionsMenu(true)
    }
    private fun hideProgressBar(){
        binding.venProgress.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar(){
        binding.venProgress.visibility = View.VISIBLE
        isLoading = true
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.vendor_menu, menu)
        val searchItem = menu.findItem(R.id.ven_menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.getVendorSearchItems(query.toString())
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    var isLoading = false

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