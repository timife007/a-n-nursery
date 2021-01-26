package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.inventory.*
import com.timife.a_n_nursery_app.inventory.data.InventoryItem
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class InventoryFragment : BaseFragment<InventoryViewModel, FragmentInventoryBinding, InventoryRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = InventAdapter(listOf(), viewModel)
        val adapter = InventAdapter(ArrayList())
        binding.recyclerView.adapter = InventAdapter(ArrayList())

//        viewModel.getAllInventoryItems.observe(viewLifecycleOwner, Observer {
//            adapter.item = it
//            adapter.notifyDataSetChanged()
//        })
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getInventoryItems()

        viewModel.products.observe(viewLifecycleOwner,{
            when(it){
               is Resource.Success ->{
                    adapter.item = it.value
               }


            }
        })

        binding.invAdd.setOnClickListener {
            val dialogFragment = AddInvItemDialog(object : AddDialogListener {
                override fun onAddButtonClicked(item: InventoryItem) {
//                    viewModel.upsert(item)
                    Toast.makeText(requireContext(),"Added to the inventory database", Toast.LENGTH_SHORT).show()
                }
            })
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
//        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.inventory_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.inv_menu_search -> {
                Toast.makeText(requireContext(), "this is search for inventory", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.inv_menu_add -> {
                Toast.makeText(requireContext(), "this is add for inventory", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.inv_menu_edit -> {
                Toast.makeText(requireContext(), "this is edit for inventory", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun getViewModel() = InventoryViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentInventoryBinding.inflate(inflater, container, false)

    override fun getRepository(): InventoryRepository {
//        val database = InventoryDatabase(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(InventoryApi::class.java, token)
        return InventoryRepository(api)
//        return InventItemRepository(database)
    }

}