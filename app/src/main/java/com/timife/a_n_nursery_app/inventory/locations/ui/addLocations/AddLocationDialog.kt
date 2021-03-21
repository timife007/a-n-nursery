package com.timife.a_n_nursery_app.inventory.locations.ui.addLocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddClassificationBinding
import com.timife.a_n_nursery_app.databinding.DialogAddLocationBinding
import com.timife.a_n_nursery_app.inventory.classifications.ui.addClassifications.AddClassificationListener

class AddLocationDialog(var addLocationListener: AddLocationListener): DialogFragment() {
    private lateinit var binding: DialogAddLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        binding = DialogAddLocationBinding.inflate(inflater)
        isCancelable = true

        binding.createLocation.setOnClickListener {
            val name = binding.locationName.text.toString()

            if (name.isEmpty()){
                Toast.makeText(requireContext(),"Please fill Information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            addLocationListener.onAddLocationButtonClicked(
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