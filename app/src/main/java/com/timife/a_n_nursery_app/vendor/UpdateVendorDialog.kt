package com.timife.a_n_nursery_app.vendor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timife.a_n_nursery_app.R

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateVendorItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateVendorItem : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner_dialog)
        return inflater.inflate(R.layout.dialog_updatedit_vendor_item, container, false)
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog?.window?.setLayout(width,ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}