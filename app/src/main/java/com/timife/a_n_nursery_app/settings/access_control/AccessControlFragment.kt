package com.timife.a_n_nursery_app.settings.access_control

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentAccessControlBinding
import com.timife.a_n_nursery_app.inventory.ui.InventoryLoadStateAdapter
import com.timife.a_n_nursery_app.settings.access_control.network.AccessControlApi
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileRepository
import kotlinx.android.synthetic.main.fragment_inventory.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AccessControlFragment : BaseFragment<AccessControlViewModel, FragmentAccessControlBinding,AccessControlRepository>() {
    private lateinit var navController:NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AccessControlAdapter(requireContext())

        binding.apply {
            accessRecyclerView.setHasFixedSize(true)
            accessRecyclerView.itemAnimator = null
            accessRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = AccessLoadStateAdapter { adapter.retry() },
                footer = AccessLoadStateAdapter { adapter.retry() }
            )
//            recycler_retry.setOnClickListener {
//                adapter.retry()
//            }
        }

        viewModel.getInvitedUser().observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getViewModel()= AccessControlViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentAccessControlBinding.inflate(inflater)

    override fun getRepository(): AccessControlRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(AccessControlApi::class.java, token)
        return AccessControlRepository(api)
    }

}