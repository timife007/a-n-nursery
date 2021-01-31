package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.inventory.data.InventoryItem
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.login.ui.handleApiError
import com.timife.a_n_nursery_app.login.ui.visible
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class InventoryFragment :
    BaseFragment<InventoryViewModel, FragmentInventoryBinding, InventoryRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getInventoryItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inventoryProgress.visible(false)

        val adapter = InventAdapter(InventAdapter.OnClickListener {
            viewModel.displayProductDetails(it)
        })
        binding.recyclerView.adapter = adapter
        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, {
            if (null != it) {
                val bottomSheet = BottomSheetDialogFragment()
                this.findNavController()
                    .navigate(
                        InventoryFragmentDirections.actionInventoryFragmentToInvntBttmShtFragment(
                            it
                        )
                    )
                viewModel.displayProductDetailsComplete()
                bottomSheet.show(requireActivity().supportFragmentManager, "BottomSheet")
            }
        })


        viewModel.products.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    binding.inventoryProgress.visible(false)
                    bindRecyclerView(recycler_view, it.value)
                    adapter.notifyDataSetChanged()

                }
                is Resource.Failure -> {
                    binding.inventoryProgress.visible(false)
                    handleApiError(it)
                }
                is Resource.Loading ->
                    binding.inventoryProgress.visible(true)
            }
        })

        binding.invAdd.setOnClickListener {
            val dialogFragment = AddInvItemDialog(object : AddDialogListener {
                override fun onAddButtonClicked(item: InventoryItem) {
                    Toast.makeText(
                        requireContext(),
                        "Added to the inventory database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
        setHasOptionsMenu(true)
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
                    viewModel.getSearchItems(query.toString())
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
        return when (item.itemId) {
            R.id.inv_menu_search -> {
                Toast.makeText(requireContext(), "this is search for inventory", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            R.id.inv_menu_add -> {
                Toast.makeText(requireContext(), "this is add for inventory", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            R.id.inv_menu_edit -> {
                Toast.makeText(requireContext(), "this is edit for inventory", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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