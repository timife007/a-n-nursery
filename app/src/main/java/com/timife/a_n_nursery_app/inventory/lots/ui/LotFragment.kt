package com.timife.a_n_nursery_app.inventory.lots.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentLocationBinding
import com.timife.a_n_nursery_app.databinding.FragmentLotBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.AddItemsFragmentDirections
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.locations.ui.*
import com.timife.a_n_nursery_app.inventory.locations.ui.addLocations.AddLocationDialog
import com.timife.a_n_nursery_app.inventory.locations.ui.addLocations.AddLocationListener
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import com.timife.a_n_nursery_app.inventory.lots.network.LotApi
import com.timife.a_n_nursery_app.inventory.lots.ui.addLots.AddLotDialog
import com.timife.a_n_nursery_app.inventory.lots.ui.addLots.AddLotListener
import com.timife.a_n_nursery_app.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LotFragment  : BaseFragment<LotViewModel, FragmentLotBinding, LotRepository>(){

    companion object {
        fun newInstance() = LotFragment()
    }

    private fun bindRecyclerView(recyclerView: RecyclerView, data: List<Lot>?) {
        val adapter = recyclerView.adapter as LotAdapter
        adapter.submitList(data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLotItems()

        binding.lotRecycler.adapter = LotAdapter(LotAdapter.OnClickListener{
            viewModel.displayEditLot(it)
        })

        viewModel.lot.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    bindRecyclerView(binding.lotRecycler,it.value.results)

                }
                is Resource.Failure ->{
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })

        viewModel.navigateToEditLot.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        AddItemsFragmentDirections.actionAddItemsFragmentToEditLotDialog(
                            it
                        )
                    )
                viewModel.displayLotEditComplete()
            }
        })

        viewModel.saveLot.observe(viewLifecycleOwner, {
            binding.lotProgress.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(),"${it.value}", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading ->{
                    showProgressBar()
                    binding.lotProgress.visible(true)
                }
            }
        })

        binding.addLot.setOnClickListener {
            val lotDialogFragment = AddLotDialog(object : AddLotListener {
                override fun onAddLotButtonClicked(lotName: String) {
                    viewModel.saveLotName(lotName)
                }
            })
            lotDialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
    }

    private fun hideProgressBar() {
        binding.lotProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.lotProgress.visibility = View.VISIBLE
    }

    override fun getViewModel()= LotViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLotBinding.inflate(inflater)

    override fun getRepository(): LotRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(LotApi::class.java, token)
//        val database = CategoryDatabase.invoke(requireContext())

        return LotRepository(api)
    }

}