package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.chip.Chip
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class InventoryFragment :
    BaseFragment<InventoryViewModel, FragmentInventoryBinding, InventoryRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInventoryBinding.bind(view)

        val adapter = InventAdapter(InventAdapter.OnClickListener {
            viewModel.displayProductDetails(it)
        })

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = InventoryLoadStateAdapter { adapter.retry() },
                footer = InventoryLoadStateAdapter { adapter.retry() }
            )
            recycler_retry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.result.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.filter.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                inventoryProgress.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                recyclerRetry.isVisible = loadState.source.refresh is LoadState.Error
                inventoryNoResultText.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    queryNoResultText.isVisible = true

                } else {
                    queryNoResultText.isVisible = false
                }
            }
        }

        val chipGroup = binding.invChipGroup
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group?.findViewById<Chip>(checkedId)
            if (chip?.text != "All") {
                viewModel.getFilterItems(chip?.text.toString())
            }else{
                viewModel.getFilterItems("")
            }
        }

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        InventoryFragmentDirections.actionInventoryFragmentToInvntBttmShtFragment(
                            it
                        )
                    )
                viewModel.displayProductDetailsComplete()
            }
        })

        viewModel.saveInventory.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "${it.value}", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        binding.invAdd.setOnClickListener {
            val dialogFragment = AddInvItemDialog(object : AddDialogListener {
                override fun onAddButtonClicked(
                    productName: String,
                    botanicalName: String,
                    size: String,
                    classification: String,
                    color: String,
                    price: String,
                    cost: String,
                    lot: String,
                    location: String,
                    quantity: String,
                    category: String
                ) {
                    viewModel.saveInventoryItems(
                        productName,
                        botanicalName,
                        size,
                        classification,
                        color,
                        price,
                        cost,
                        lot,
                        location,
                        quantity,
                        category
                    )
                }

            })
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
        setHasOptionsMenu(true)
    }

    private fun hideProgressBar() {
        binding.inventoryProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.inventoryProgress.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.inventory_menu, menu)
        val searchItem = menu.findItem(R.id.inv_menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.getSearchInventory(query)
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

    override fun getViewModel() = InventoryViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentInventoryBinding.inflate(inflater, container, false)

    override fun getRepository(): InventoryRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(InventoryApi::class.java, token)
        return InventoryRepository(api)
    }
}