package com.timife.a_n_nursery_app.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.activities.network.ActivitiesApi
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentActivitiesBinding
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventAdapter
import com.timife.a_n_nursery_app.inventory.ui.InventoryLoadStateAdapter
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import kotlinx.android.synthetic.main.activities_load_state_footer.*
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ActivitiesFragment : BaseFragment<ActivitiesViewModel,FragmentActivitiesBinding,ActivitiesRepository>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentActivitiesBinding.bind(view)

        val adapter = ActivitiesAdapter(requireContext())

        binding.apply {
            activitiesRecyclerView.setHasFixedSize(true)
            activitiesRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ActivitiesLoadStateAdapter { adapter.retry() },
                footer = ActivitiesLoadStateAdapter { adapter.retry() }
            )
            actRecyclerRetry.setOnClickListener {
                adapter.retry()
            }
        }


        viewModel.result.observe(viewLifecycleOwner, Observer{
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
            adapter.notifyDataSetChanged()
        })


        adapter.addLoadStateListener { loadState ->
            binding.apply {
                actProgress.isVisible = loadState.refresh is LoadState.Loading
                activitiesRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
                actRecyclerRetry.isVisible = loadState.refresh is LoadState.Error
                actQueryNoResultText.isVisible = loadState.refresh is LoadState.Error

            }
        }

    }

    private fun hideProgressBar() {
        binding.actProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.actProgress.visibility = View.VISIBLE
    }

    override fun getViewModel()= ActivitiesViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentActivitiesBinding.inflate(inflater)

    override fun getRepository(): ActivitiesRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(ActivitiesApi::class.java, token)
        return ActivitiesRepository(api)
    }

}