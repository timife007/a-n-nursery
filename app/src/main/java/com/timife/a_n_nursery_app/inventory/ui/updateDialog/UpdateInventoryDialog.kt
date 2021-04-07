package com.timife.a_n_nursery_app.inventory.ui.updateDialog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.DialogUpdateditInventoryItemsBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.data.InventoryDatabase
import com.timife.a_n_nursery_app.inventory.lots.network.LotApi
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository
import com.timife.a_n_nursery_app.inventory.lots.ui.editLots.EditLotViewModel
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class UpdateInventoryDialog: DialogFragment() {
    private val retrofitClient = RetrofitClient()
    private lateinit var userPreferences: UserPreferences

    private lateinit var binding : DialogUpdateditInventoryItemsBinding
    private lateinit var viewModel : UpdateInventoryDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogUpdateditInventoryItemsBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val product = UpdateInventoryDialogArgs.fromBundle(requireArguments()).selectedEdit
        val productId = UpdateInventoryDialogArgs.fromBundle(requireArguments()).selectedEdit.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first()}
        val api = retrofitClient.buildApi(InventoryApi::class.java, token)
        val database = CategoryDatabase.invoke(requireContext())
        val inventoryRepository = InventoryRepository(api,database)

        val viewModelFactory = UpdateInventoryViewModelFactory(product, application,inventoryRepository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateInventoryDialogViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateInventoryDialogViewModel::class.java)

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.updateProduct.setOnClickListener {
            val productName = binding.productName.text.toString()
            val botanicalName = binding.botanical.text.toString()
            val size = binding.sizeText.text.toString()
            val classification = binding.classification.text.toString()
            val color = binding.color.text.toString()
            val price = binding.price.text.toString()
            val cost = binding.cost.text.toString()
            val lot = binding.lot.text.toString()
            val location = binding.location1.text.toString()
            val quantity = binding.quantity.text.toString().toInt()
            val category = binding.categoryName.text.toString()

            viewModel.updateInventoryItem(
                productId,
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
            viewModel.updateInventoryItem.observe(viewLifecycleOwner, Observer{
                when(it){
                    is Resource.Success ->{
                        Toast.makeText(requireContext(),"Product Updated Successfully",Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(),"Updating...",Toast.LENGTH_LONG).show()
                    }
                    is Resource.Failure ->{
                        handleApiError(it)
                        Toast.makeText(requireContext(),"Failed to Update",Toast.LENGTH_SHORT).show()
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
}