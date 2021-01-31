package com.timife.a_n_nursery_app.inventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddInventoryItemBinding
import com.timife.a_n_nursery_app.inventory.data.InventoryItem

class AddInvItemDialog(var addDialogListener: AddDialogListener) : DialogFragment() {
    private lateinit var binding: DialogAddInventoryItemBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddInventoryItemBinding.inflate(inflater)
        isCancelable = false
        val dropdownList = listOf("Fruits", "Flowers", "Trees", "Ornamental", "Perennial", "Biennial")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, dropdownList)
        (binding.invCategoriesContainer.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.invCategories.setText("Categories", false)

//        val sizeDropdown = listOf("Inch", "Gallon", "Pound", "Small", "Medium", "Large","X-Large","Pallet","Piece")
//        val sizeAdapter = ArrayAdapter(requireContext(), R.layout.size_item, sizeDropdown)
//        (binding.sizeUnit.editText as? AutoCompleteTextView)?.setAdapter(sizeAdapter)
//        binding.sizeUnitText.setText("Size", false)

        binding.save.setOnClickListener {
            val name = binding.productName.text.toString()
            val variant = binding.botanical.text.toString()
            val lot = binding.lot.text.toString()
            val cost = binding.cost.text.toString()
            val price = binding.price.text.toString()

            val categories = binding.invCategories.text.toString()
            if (name.isEmpty() || variant.isEmpty() || lot.isEmpty() || cost.isEmpty() || price.isEmpty() || categories.isEmpty()) {

                Toast.makeText(context, "Please fill information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = InventoryItem(name, variant, lot, cost, price)
            addDialogListener.onAddButtonClicked(item)
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