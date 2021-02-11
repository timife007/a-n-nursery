package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddInventoryItemBinding

class AddInvItemDialog(var addDialogListener: AddDialogListener) : DialogFragment() {
    private lateinit var binding: DialogAddInventoryItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddInventoryItemBinding.inflate(inflater)
        isCancelable = false

        binding.save.setOnClickListener {
            val productName = binding.productName.text.toString()
            val botanicalName = binding.botanical.text.toString()
            val lot = binding.lot.text.toString()
            val cost = binding.cost.text.toString()
            val price = binding.price.text.toString()
            val classification = binding.classification.text.toString()
            val size = binding.sizeText.text.toString()
            val quantity = binding.quantity.text.toString()
            val color = binding.color.text.toString()
            val category = binding.categoryName.text.toString()
            val location  = binding.location1.text.toString()

            if (productName.isEmpty() || botanicalName.isEmpty() || lot.isEmpty() || cost.isEmpty() || price.isEmpty() || category.isEmpty() || classification.isEmpty() || size.isEmpty() || quantity.isEmpty() || color.isEmpty() || location.isEmpty()) {

                Toast.makeText(context, "Please fill information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addDialogListener.onAddButtonClicked(productName,botanicalName,size,classification,color,price,cost,lot,location,quantity,category)
            dismiss()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}