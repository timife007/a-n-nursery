package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.chip.Chip
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import com.timife.a_n_nursery_app.inventory.locations.database.LocationItem
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog.AddDialogListener
import com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog.AddInvItemDialog
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class InventoryFragment :
    BaseFragment<InventoryViewModel, FragmentInventoryBinding, InventoryRepository>() {
    var swipeCount = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLocationItems()
        viewModel.getCategoryItems()
        viewModel.getLotItems()
        viewModel.getClassificationItems()

        binding = FragmentInventoryBinding.bind(view)

        val adapter = InventAdapter(requireContext(), InventAdapter.OnClickListener {
            viewModel.displayProductDetails(it)
        }, InventAdapter.OnDeleteListener {
            viewModel.deleteInventoryItem(it)
        })

//        var data = PagingData
        binding.swipeRefreshInventory.setOnRefreshListener {
            swipeCount += 1
            if (swipeCount > 0) {
                viewModel.result.observe(viewLifecycleOwner) {
                    adapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
                adapter.notifyDataSetChanged()
                binding.swipeRefreshInventory.isRefreshing = false
            }
        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
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

        viewModel.filter.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.category.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    try {
                        val categoryList = it.value.results
                        categoryList.forEach {
                            val category = CategoryItem(it.id!!, it.name!!)
                            viewModel.upsert(listOf(category))
                        }

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        viewModel.location.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    try {
                        val locationList = it.value.results
                        locationList.forEach {
                            val location = LocationItem(it.id!!, it.name!!)
                            viewModel.upsertLocation(listOf(location))
                        }

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        viewModel.lots.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    try {
                        val lotsList = it.value.results
                        lotsList.forEach {
                            val lot = LotItem(it.id!!, it.name)
                            viewModel.upsertLot(listOf(lot))
                        }

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        viewModel.classification.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    try {
                        val classificationList = it.value.results
                        classificationList.forEach {
                            val classification = ClassificationItem(it.id, it.name)
                            viewModel.upsertClassification(listOf(classification))
                        }

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                inventoryProgress.isVisible = loadState.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.refresh is LoadState.NotLoading
                recyclerRetry.isVisible = loadState.refresh is LoadState.Error
                inventoryNoResultText.isVisible = loadState.refresh is LoadState.Error
//                if(loadState.append.endOfPaginationReached){
//                    inventoryNoResultText.isVisible =false
//                    queryNoResultText.isVisible = false
//                }
//                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
//                    adapter.itemCount < 1
//                ) {
//                    recyclerView.isVisible = false
//                    queryNoResultText.isVisible = true
//                } else {
//                    queryNoResultText.isVisible = false
//                }
            }
        }

        val chipGroup = binding.invChipGroup
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group?.findViewById<Chip>(checkedId)
            if (chip?.text != "All") {
                viewModel.getFilterItems(chip?.text.toString())
            } else {
                viewModel.getFilterItems("")
            }
        }

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner) {
            this.findNavController()
                .navigate(
                    InventoryFragmentDirections.actionInventoryFragmentToInvntBttmShtFragment(
                        it
                    )
                )
            viewModel.displayProductDetailsComplete()
        }

        viewModel.saveInventory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        binding.invAdd.setOnClickListener {
            val dialogFragment = AddInvItemDialog(object : AddDialogListener {
                override fun onAddButtonClicked(
                    productName: String,
                    botanicalName: String,
                    size: String,
                    classification: Int,
                    color: String,
                    price: String,
                    cost: String,
                    lot: Int,
                    location: Int,
                    quantity: Int,
                    category: Int
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.inv_add_items ->
                this.findNavController().navigate(R.id.action_inventoryFragment_to_addItemsFragment)

        }
        return true
    }

    override fun getViewModel() = InventoryViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentInventoryBinding.inflate(inflater, container, false)

    override fun getRepository(): InventoryRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(InventoryApi::class.java, token)
        val database = CategoryDatabase.invoke(requireContext())

        return InventoryRepository(api, database)
    }
}