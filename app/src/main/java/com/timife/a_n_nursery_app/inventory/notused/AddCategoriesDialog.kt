package com.timife.a_n_nursery_app.inventory.notused

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.timife.a_n_nursery_app.databinding.DialogAddCategoriesBinding

class AddCategoriesDialog(context: Context, var addCategDialogListener: AddCategDialogListener) :
    AppCompatDialog(context) {
    private lateinit var binding: DialogAddCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddCategoriesBinding.inflate(layoutInflater)

    }
}