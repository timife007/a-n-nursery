package com.timife.a_n_nursery_app.inventory.categories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentCategoryBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.AddItemsFragmentDirections
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.categories.ui.addCategories.AddCategoryDialog
import com.timife.a_n_nursery_app.inventory.categories.ui.addCategories.AddCategoryListener
import com.timife.a_n_nursery_app.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CategoryFragment :
    BaseFragment<CategoryViewModel, FragmentCategoryBinding, CategoryRepository>() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    var swipeCount = 0

    private fun bindRecyclerView(recyclerView: RecyclerView, data: List<Category>?) {
        val adapter = recyclerView.adapter as CategoryAdapter
        adapter.submitList(data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategoryItems()

        val adapter = CategoryAdapter(CategoryAdapter.OnClickListener {
            viewModel.displayEditCategory(it)
        }, CategoryAdapter.OnDeleteListener {
            viewModel.deleteCategoryItem(it)
        }, requireContext())

        binding.categoryRecycler.adapter = adapter
        val data = ArrayList<Category>()

        binding.swipeRefreshCategory.setOnRefreshListener {
            swipeCount += 1

            if (swipeCount > 0) {
                bindRecyclerView(binding.categoryRecycler,data)
//                adapter.submitList(data)
            }
            binding.swipeRefreshCategory.isRefreshing = true
        }



        viewModel.category.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    bindRecyclerView(binding.categoryRecycler, it.value.results)

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

        viewModel.navigateToEditCategory.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController()
                    .navigate(
                        AddItemsFragmentDirections.actionAddItemsFragmentToEditCategoryDialog(
                            it
                        )
                    )
                viewModel.displayProductDetailsComplete()
            }
        })

        viewModel.saveCategory.observe(viewLifecycleOwner, Observer {
            binding.categoryProgress.visible(it is Resource.Loading)
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
                    binding.categoryProgress.visible(true)
                }
            }
        })
        binding.addCategory.setOnClickListener {
            val categoryDialogFragment = AddCategoryDialog(object : AddCategoryListener {
                override fun onAddCategoryButtonClicked(categoryName: String) {
                    viewModel.saveCategoryItem(categoryName)
                }
            })
            categoryDialogFragment.show(requireActivity().supportFragmentManager, "signature")
        }
    }

    private fun hideProgressBar() {
        binding.categoryProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.categoryProgress.visibility = View.VISIBLE
    }

    override fun getViewModel() = CategoryViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCategoryBinding.inflate(inflater)

    override fun getRepository(): CategoryRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(CategoryApi::class.java, token)
        return CategoryRepository(api)
    }

}