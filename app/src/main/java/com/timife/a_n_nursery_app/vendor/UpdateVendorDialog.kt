package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.DialogUpdateditInventoryItemsBinding
import com.timife.a_n_nursery_app.databinding.DialogUpdateditVendorItemBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.inventory.ui.updateDialog.UpdateInventoryDialogArgs
import com.timife.a_n_nursery_app.inventory.ui.updateDialog.UpdateInventoryDialogViewModel
import com.timife.a_n_nursery_app.inventory.ui.updateDialog.UpdateInventoryViewModelFactory
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateVendorItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateVendorDialog : DialogFragment() {

    private val retrofitClient = RetrofitClient()
    private lateinit var userPreferences: UserPreferences

    private lateinit var binding : DialogUpdateditVendorItemBinding
    private lateinit var viewModel : UpdateVendorDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogUpdateditVendorItemBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val vendor = UpdateInventoryDialogArgs.fromBundle(requireArguments()).selectedEdit
        val vendorId = UpdateInventoryDialogArgs.fromBundle(requireArguments()).selectedEdit.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first()}
        val api = retrofitClient.buildApi(InventoryApi::class.java, token)
        val database = CategoryDatabase.invoke(requireContext())
        val inventoryRepository = InventoryRepository(api,database)

        val viewModelFactory = UpdateInventoryViewModelFactory(vendor, application,inventoryRepository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateVendorDialogViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateVendorDialogViewModel::class.java)

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.save.setOnClickListener {
            val firstName = binding.vendorName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.text.toString()
            val company = binding.company.text.toString()
            val type = binding.type.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()
//            val cost = binding.cost.text.toString()
//            val lot = binding.lot.text.toString()
//            val location = binding.location1.text.toString()
//            val quantity = binding.quantity.text.toString().toInt()
//            val category = binding.categoryName.text.toString()

            viewModel.updateVendorItem(
                vendorId,
                firstName,
                lastName,
                email,
                company,
                type,
                phoneNumber
            )
            viewModel.updateVendorItem.observe(viewLifecycleOwner, Observer{
                when(it){
                    is Resource.Success ->{
                        Toast.makeText(requireContext(),"Product Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(),"Updating...", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Failure ->{
                        handleApiError(it)
                        Toast.makeText(requireContext(),"Failed to Update", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(UpdateInventoryDialogViewModel::class.java)
//        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
//        return inflater.inflate(R.layout.dialog_updatedit_vendor_item, container, false)
//    }

}