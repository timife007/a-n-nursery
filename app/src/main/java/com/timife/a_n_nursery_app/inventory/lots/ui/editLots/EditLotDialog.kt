package com.timife.a_n_nursery_app.inventory.lots.ui.editLots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogEditLotBinding


class EditLotDialog: DialogFragment() {
    private lateinit var binding : DialogEditLotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        val application = requireNotNull(activity).application
        binding = DialogEditLotBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val lot = EditLotDialogArgs.fromBundle(requireArguments()).selectedEditLot
        val viewModelFactory = EditLotViewModelFactory(lot, application)
        binding.viewModel = ViewModelProvider(this,viewModelFactory).get(EditLotViewModel::class.java)


//        binding.updateLot.setOnClickListener {
//            val name = binding.lotName.text.toString()
//            if(name == name ){
//                Toast.makeText(requireContext(),"Lot name remains unchanged",Toast.LENGTH_SHORT).show()
//            }
//            if (name.isEmpty()){
//                Toast.makeText(requireContext(),"Please fill lot information",Toast.LENGTH_SHORT).show()
//            }
//            updateLot()
//            dismiss()
//        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun updateLot() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}