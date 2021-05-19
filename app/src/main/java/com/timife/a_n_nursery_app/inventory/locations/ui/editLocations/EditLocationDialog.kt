package com.timife.a_n_nursery_app.inventory.locations.ui.editLocations

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
import com.timife.a_n_nursery_app.databinding.DialogEditLocationBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class EditLocationDialog : DialogFragment() {
    private val retrofitClient = RetrofitClient()
    private lateinit var userPreferences: UserPreferences

    private lateinit var binding: DialogEditLocationBinding
    private lateinit var viewModel: EditLocationViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val location = EditLocationDialogArgs.fromBundle(requireArguments()).selectedEditLocation
        val locationId =
            EditLocationDialogArgs.fromBundle(requireArguments()).selectedEditLocation.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(LocationApi::class.java, token)
        val locationRepository = LocationRepository(api)
        val viewModelFactory =
            EditLocationViewModelFactory(location, application, locationRepository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditLocationViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditLocationViewModel::class.java)

        binding.updateLocation.setOnClickListener {
            val locationName = binding.locationName.text.toString()
            if (locationId != null) {
                viewModel.updateLocation(locationId, locationName)


                //Update check
                viewModel.updateLocation.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Location Updated Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        }
                        is Resource.Failure -> {
                            handleApiError(it)
                        }
                        is Resource.Loading -> {
                            Toast.makeText(requireContext(), "Updating...", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }


        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}