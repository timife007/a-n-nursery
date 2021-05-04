package com.timife.a_n_nursery_app.inventory.classifications.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentClassificationBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.AddItemsFragmentDirections
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationApi
import com.timife.a_n_nursery_app.inventory.classifications.ui.addClassifications.AddClassificationDialog
import com.timife.a_n_nursery_app.inventory.classifications.ui.addClassifications.AddClassificationListener
import com.timife.a_n_nursery_app.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ClassificationFragment :
    BaseFragment<ClassificationViewModel, FragmentClassificationBinding, ClassificationRepository>() {

    companion object {
        fun newInstance() = ClassificationFragment()
    }


    private fun bindRecyclerView(recyclerView: RecyclerView, data: List<Classification>?) {
        val adapter = recyclerView.adapter as ClassificationAdapter
        adapter.submitList(data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getClassificationItems()

        binding.classificationRecycler.adapter =
            ClassificationAdapter(ClassificationAdapter.OnClickListener {
                viewModel.displayEditClassification(it)
            }, ClassificationAdapter.OnDeleteListener {
                viewModel.deleteClassification(it)
            }, requireContext())

        viewModel.classification.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    bindRecyclerView(binding.classificationRecycler, it.value.results)
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.navigateToEditClassification.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (null != it) {
                    this.findNavController()
                        .navigate(
                            AddItemsFragmentDirections.actionAddItemsFragmentToEditClassificationDialog(
                                it
                            )
                        )
                    viewModel.displayClassificationEditComplete()
                }
            })

        viewModel.saveClassification.observe(viewLifecycleOwner) { it ->
            binding.classificationProgress.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                    binding.classificationProgress.visible(true)
                }
            }
        }

        binding.addClassification.setOnClickListener {
            val classificationDialogFragment =
                AddClassificationDialog(object : AddClassificationListener {
                    override fun onAddClassificationButtonClicked(classificationName: String) {
                        viewModel.saveClassificationItem(classificationName)
                    }
                })
            classificationDialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
    }

    private fun hideProgressBar() {
        binding.classificationProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.classificationProgress.visibility = View.VISIBLE
    }

    override fun getViewModel() = ClassificationViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentClassificationBinding.inflate(inflater)

    override fun getRepository(): ClassificationRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(ClassificationApi::class.java, token)
        return ClassificationRepository(api)
    }

}