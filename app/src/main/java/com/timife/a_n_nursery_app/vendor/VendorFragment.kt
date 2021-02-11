package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.*
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentVendorBinding
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import com.timife.a_n_nursery_app.vendor.response.Vendors
import kotlinx.android.synthetic.main.fragment_inventory.recycler_view
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class VendorFragment : BaseFragment<VendorViewModel, FragmentVendorBinding, VendorRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.venProgress.visible(false)

        val adapter = VendorAdapter(VendorAdapter.OnClickListener {
            viewModel.displayVendorDetails(it)
        })
        binding.recyclerView.adapter = adapter
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
                    Toast.makeText(requireContext(),"${it.value}",Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                is Resource.Loading ->{
                    binding.venProgress.visible(true)
                }
            }
        })

        viewModel.vendors.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    bindVenRecyclerView(recycler_view, it.value)
                    adapter.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                        hideProgressBar()
                    handleApiError(it)
                    Toast.makeText(requireContext(), "Error loading vendors ${it.errorBody}", Toast.LENGTH_LONG)
                            .show()
                }
                is Resource.Loading -> {
                    showProgressBar()

                }
            }
        })

        viewModel.search.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    bindVenRecyclerView(binding.recyclerView, it.value)
                    adapter.notifyDataSetChanged()
                    val totalPages = it.value.count / Constants.QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.searchPage == totalPages

                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading ->
                    showProgressBar()
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
    private fun bindVenRecyclerView(recyclerView: RecyclerView, data: Vendors) {
        val adapter = recyclerView.adapter as? VendorAdapter
        adapter?.submitList(data.results.toList())
        recyclerView.addOnScrollListener(this.scrollListener)

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
                    viewModel.getVendorSearchItems(query.toString(),query.toString())
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
    var isLastPage = false
    var isScrolling = false

    private val scrollListener =object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager  = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage =  !isLoading   &&  !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getVendorItems()
                isScrolling = false
            }else{
                binding.recyclerView.setPadding(0,0,0,0)
            }
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