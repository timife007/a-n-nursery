package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddVenItemBinding

class AddVenItemDialog(var addDialogListener: AddVenDialogListener) : DialogFragment() {
    private lateinit var binding: DialogAddVenItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddVenItemBinding.inflate(inflater)
        isCancelable = false
        val dropdownList = listOf("Wholesale", "Retail")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, dropdownList)
        (binding.typeContainer.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.type.setText("Type", false)

        binding.save.setOnClickListener {
            val name = binding.vendorName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()
            val company = binding.company.text.toString()

            val type = binding.type.text.toString()
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || company.isEmpty() || type.isEmpty() || type == "Type") {

                Toast.makeText(context, "Please fill information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addDialogListener.onAddVenButtonClicked(
                name,
                lastName,
                email,
                company,
                type,
                phoneNumber
            )
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