package com.timife.a_n_nursery_app.inventory.ui.updateDialog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogUpdateditInventoryItemsBinding

class UpdateInventoryDialog: DialogFragment() {

    private lateinit var binding : DialogUpdateditInventoryItemsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogUpdateditInventoryItemsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = UpdateInventoryDialogArgs.fromBundle(requireArguments()).selectedEdit
        val viewModelFactory = UpdateInventoryViewModelFactory(product, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(UpdateInventoryDialogViewModel::class.java)
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