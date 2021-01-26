package com.timife.a_n_nursery_app.settings.companyInfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentCompanyInfoBinding

class CompanyInfoFragment : BaseFragment<CompanyInfoViewModel, FragmentCompanyInfoBinding, CompanyInfoRepository>() {
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        navController = findNavController()
        val dropdownList = listOf("Buying", "Investing", "Selling")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, dropdownList)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.businessType.setText("Business Type", false)
    }

    override fun getViewModel() = CompanyInfoViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentCompanyInfoBinding.inflate(inflater, container, false)

    override fun getRepository(): CompanyInfoRepository {
        return CompanyInfoRepository()
    }

}