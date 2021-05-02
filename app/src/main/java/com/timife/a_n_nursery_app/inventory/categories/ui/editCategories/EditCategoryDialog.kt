package com.timife.a_n_nursery_app.inventory.categories.ui.editCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.DialogEditCategoryBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class EditCategoryDialog: DialogFragment(){
    private val retrofitClient = RetrofitClient()
    private lateinit var userPreferences: UserPreferences

    private lateinit var binding : DialogEditCategoryBinding
    private lateinit var viewModel : EditCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val category = EditCategoryDialogArgs.fromBundle(requireArguments()).selectedEditCategory
        val categoryId = EditCategoryDialogArgs.fromBundle(requireArguments()).selectedEditCategory.id

        //Dependency Injection needed
        userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(CategoryApi::class.java, token)
        val categoryRepository = CategoryRepository(api)

        val viewModelFactory = EditCategoryViewModelFactory(category, application,categoryRepository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditCategoryViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditCategoryViewModel::class.java)

        binding.updateCategory.setOnClickListener {
            val categoryName = binding.categoryName.text.toString()
            if (categoryId != null) {
                viewModel.updateCategory(categoryId,categoryName)

                //Update check
                viewModel.updateCategory.observe(viewLifecycleOwner){
                    when(it){
                        is Resource.Success -> {
                            Toast.makeText(requireContext(),"$it Updated Successfully",Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                        is Resource.Failure ->{
                            handleApiError(it)
                        }
                        is Resource.Loading ->{
                            Toast.makeText(requireContext(),"Updating...",Toast.LENGTH_LONG).show()
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

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}