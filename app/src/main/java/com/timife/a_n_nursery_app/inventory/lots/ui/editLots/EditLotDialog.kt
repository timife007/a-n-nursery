package com.timife.a_n_nursery_app.inventory.lots.ui.editLots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.DialogEditLotBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.inventory.locations.ui.editLocations.EditLocationViewModel
import com.timife.a_n_nursery_app.inventory.lots.network.LotApi
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class EditLotDialog: DialogFragment() {
    private val retrofitClient = RetrofitClient()
    private lateinit var userPreferences: UserPreferences
    private lateinit var binding : DialogEditLotBinding
    private lateinit var viewModel : EditLotViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditLotBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val lot = EditLotDialogArgs.fromBundle(requireArguments()).selectedEditLot
        val lotId = EditLotDialogArgs.fromBundle(requireArguments()).selectedEditLot.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(LotApi::class.java, token)
        val lotRepository = LotRepository(api)

        val viewModelFactory = EditLotViewModelFactory(lot, application,lotRepository)
        binding.viewModel = ViewModelProvider(this,viewModelFactory).get(EditLotViewModel::class.java)
        viewModel = ViewModelProvider(this,viewModelFactory).get(EditLotViewModel::class.java)

        binding.updateLot.setOnClickListener {
            val lotName = binding.lotName.text.toString()
            viewModel.updateLot(lotId,lotName)

            //Update check
            viewModel.updateLot.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Success -> {
                        Toast.makeText(requireContext(),"Location Updated Successfully", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    is Resource.Failure ->{
                        handleApiError(it)
                    }
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(),"Updating...", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun updateLot() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}