package com.timife.a_n_nursery_app.settings.access_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.DialogAddLocationBinding
import com.timife.a_n_nursery_app.databinding.DialogInviteUserBinding
import com.timife.a_n_nursery_app.inventory.locations.ui.addLocations.AddLocationListener

class InviteUserDialog (var inviteDialogListener: InviteDialogListener): DialogFragment() {
    private lateinit var binding: DialogInviteUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        binding = DialogInviteUserBinding.inflate(inflater)
        isCancelable = true

        binding.inviteUser.setOnClickListener {
            val name = binding.userName.text.toString()
            val email = binding.userEmail.text.toString()

            if (name.isEmpty() || email.isEmpty()){
                Toast.makeText(requireContext(),"Please fill Information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            inviteDialogListener.onInviteButtonClicked(
                email,name
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