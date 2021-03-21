package com.timife.a_n_nursery_app.inventory.categories.ui.addCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddCategoryBinding
import com.timife.a_n_nursery_app.databinding.DialogAddClassificationBinding
import com.timife.a_n_nursery_app.inventory.classifications.ui.addClassifications.AddClassificationListener

class AddCategoryDialog (var addCategoryListener: AddCategoryListener): DialogFragment() {
    private lateinit var binding: DialogAddCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        binding = DialogAddCategoryBinding.inflate(inflater)
        isCancelable = true

        binding.createCategory.setOnClickListener {
            val name = binding.categoryName.text.toString()

            if (name.isEmpty()){
                Toast.makeText(requireContext(),"Please fill Information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addCategoryListener.onAddCategoryButtonClicked(
                name
            )
            dismiss()
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