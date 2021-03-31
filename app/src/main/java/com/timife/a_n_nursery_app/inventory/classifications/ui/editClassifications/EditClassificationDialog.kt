package com.timife.a_n_nursery_app.inventory.classifications.ui.editClassifications

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
import com.timife.a_n_nursery_app.databinding.DialogEditClassificationBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationApi
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class EditClassificationDialog: DialogFragment(){
    private lateinit var userPreferences: UserPreferences
    private val retrofitClient =  RetrofitClient()
    private lateinit var viewModel: EditClassificationViewModel
    private lateinit var binding : DialogEditClassificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditClassificationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val classification = EditClassificationDialogArgs.fromBundle(requireArguments()).selectedEditClassification
        val classificationId = EditClassificationDialogArgs.fromBundle(requireArguments()).selectedEditClassification.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(ClassificationApi::class.java, token)
        val classificationRepository = ClassificationRepository(api)
        val viewModelFactory = EditClassificationViewModelFactory(classification, application,classificationRepository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditClassificationViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditClassificationViewModel::class.java)

        binding.updateClassification.setOnClickListener {
            val classificationName = binding.classificationName.text.toString()
            viewModel.updateClassification(classificationId, classificationName)


            //Update check
            viewModel.updateClassification.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Success -> {
                        Toast.makeText(requireContext(),"Classification Updated Successfully", Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}