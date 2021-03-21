package com.timife.a_n_nursery_app.inventory.locations.ui.editLocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogEditLocationBinding

class EditLocationDialog: DialogFragment() {
    private lateinit var binding : DialogEditLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val location = EditLocationDialogArgs.fromBundle(requireArguments()).selectedEditLocation
        val viewModelFactory = EditLocationViewModelFactory(location, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditLocationViewModel::class.java)

        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}