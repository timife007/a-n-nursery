package com.timife.a_n_nursery_app.inventory.ui.addInventoryDialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddInventoryItemBinding
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.classifications.database.ClassificationItem
import kotlinx.android.synthetic.main.dialog_updatedit_inventory_items.*

class AddInvItemDialog(var addDialogListener: AddDialogListener) : DialogFragment() {
    private lateinit var binding: DialogAddInventoryItemBinding

    private var categoryId: Int = 0
    private var classificationId: Int = 0
    private var locationId: Int = 0
    private var lotId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        binding = DialogAddInventoryItemBinding.inflate(inflater)
        isCancelable = false
        val categoryDatabase = CategoryDatabase(requireContext())
        val addInvRepository = AddInvRepository(categoryDatabase)
        val factory = AddInvItemViewModelFactory(addInvRepository)

        val viewModel = ViewModelProvider(this, factory).get(AddInvItemViewModel::class.java)


//        initData()
        val categoryAdapter =
            ArrayAdapter<Any>(requireContext(), android.R.layout.simple_dropdown_item_1line)
        val lotAdapter =
            ArrayAdapter<Any>(requireContext(), android.R.layout.simple_dropdown_item_1line)
        val locationAdapter =
            ArrayAdapter<Any>(requireContext(), android.R.layout.simple_dropdown_item_1line)
        val classificationAdapter =
            ArrayAdapter<Any>(requireContext(), android.R.layout.simple_dropdown_item_1line)




        viewModel.getAllCategoryItems.observe(viewLifecycleOwner, Observer<List<CategoryItem>> {
            it.forEach {
                categoryAdapter.add(it.name)
            }
            val categoryList = it
            binding.invCategoriesContainer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        categoryId = categoryList[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        })

        binding.invCategoriesContainer.adapter = categoryAdapter


        viewModel.getAllLotItems.observe(viewLifecycleOwner) {
            it.forEach {
                lotAdapter.add(it.name)
            }

            val lotsList = it
            binding.lotContainer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        lotId = lotsList[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
        binding.lotContainer.adapter = lotAdapter


        viewModel.getAllLocationItems.observe(viewLifecycleOwner) {
            it.forEach {
                locationAdapter.add(it.name)
            }
            val locationList = it
            binding.location1Container.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        locationId = locationList[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }

        binding.location1Container.adapter = locationAdapter

        viewModel.getAllClassificationItems.observe(viewLifecycleOwner) {
            it.forEach {
                classificationAdapter.add(it.name)
            }
            val classificationList = it
            binding.classificationContainer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        classificationId = classificationList[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }

        binding.classificationContainer.adapter = classificationAdapter


        binding.save.setOnClickListener {
            val productName = binding.productName.text.toString()
            val botanicalName = binding.botanical.text.toString()
            val lot = lotId
            val cost = binding.cost.text.toString()
            val price = binding.price.text.toString()
            val classification = classificationId
            val size = binding.sizeText.text.toString()
            var quantityData = binding.quantity.text.toString()
            val color = binding.color.text.toString()
            val category = categoryId
            val location = locationId
            var quantity: Int;

            if (productName.isEmpty() || botanicalName.isEmpty() || cost.isEmpty() ||
                price.isEmpty() || size.isEmpty() || color.isEmpty()
            ) {
                Toast.makeText(context, "Please fill information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (quantityData.isEmpty()) {
                Toast.makeText(context, "Please fill in quantity", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                quantity = quantityData.toInt()
            }

            addDialogListener.onAddButtonClicked(
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