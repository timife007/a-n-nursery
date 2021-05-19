package com.timife.a_n_nursery_app.inventory.locations.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentLocationBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.AddItemsFragmentDirections
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.locations.ui.addLocations.AddLocationDialog
import com.timife.a_n_nursery_app.inventory.locations.ui.addLocations.AddLocationListener
import com.timife.a_n_nursery_app.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LocationFragment :
    BaseFragment<LocationViewModel, FragmentLocationBinding, LocationRepository>() {
    var swipeCount = 0

    companion object {
        fun newInstance() = LocationFragment()
    }

    private fun bindRecyclerView(recyclerView: RecyclerView, data: List<Location>?) {
        val adapter = recyclerView.adapter as LocationAdapter
        adapter.submitList(data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLocationItems()




        binding.locationRecycler.adapter = LocationAdapter(LocationAdapter.OnClickListener {
            viewModel.displayEditLocation(it)
        }, LocationAdapter.OnDeleteListener {
            viewModel.deleteLocationItem(it)
        }, requireContext())


        val data = ArrayList<Location>()
        binding.swipeRefreshLocation.setOnRefreshListener {
            swipeCount += 1


            if (swipeCount > 0) {
                bindRecyclerView(binding.locationRecycler,data)
            }
            viewModel.getLocationItems()

            binding.swipeRefreshLocation.isRefreshing = false
        }

        viewModel.location.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    bindRecyclerView(binding.locationRecycler, it.value.results)

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

        viewModel.navigateToEditLocation.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        AddItemsFragmentDirections.actionAddItemsFragmentToEditLocationDialog(
                            it
                        )
                    )
                viewModel.displayLocationEditComplete()
            }
        })


        viewModel.saveLocation.observe(viewLifecycleOwner) {
            binding.locationProgress.visible(it is Resource.Loading)
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
                    binding.locationProgress.visible(true)
                }
            }
        }

        binding.addLocation.setOnClickListener {
            val locationDialogFragment = AddLocationDialog(object : AddLocationListener {
                override fun onAddLocationButtonClicked(locationName: String) {
                    viewModel.saveLocationName(locationName)
                }
            })
            locationDialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
    }


    private fun hideProgressBar() {
        binding.locationProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.locationProgress.visibility = View.VISIBLE
    }

    override fun getViewModel() = LocationViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLocationBinding.inflate(inflater)

    override fun getRepository(): LocationRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(LocationApi::class.java, token)
        return LocationRepository(api)
    }

}