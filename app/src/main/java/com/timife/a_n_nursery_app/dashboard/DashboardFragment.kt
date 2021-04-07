package com.timife.a_n_nursery_app.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.dashboard.network.DashboardApi
import com.timife.a_n_nursery_app.databinding.FragmentDashboardBinding
import com.timife.a_n_nursery_app.databinding.FragmentInventoryBinding
import com.timife.a_n_nursery_app.handleApiError
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryItem
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.inventory.ui.InventoryViewModel
import com.timife.a_n_nursery_app.specs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding, DashBoardRepository>() {
//    private lateinit var binding: FragmentDashboardBinding
//    private lateinit var viewModel: DashboardViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "DashBoard"
//        showProgressBar()
        viewModel.getDashboard()

        val spinnerItems = arrayOf("Today", "Yesterday", "Last Week")
        binding.spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            spinnerItems
        )

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerItems.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        viewModel.dashboard.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.inventoryProdNum.text = it.value.low_inventory.toString()
                    binding.productNumber.text = it.value.products_sold.toString()
                    binding.transactionNumber.text =  it.value.transactions.toString()
                    binding.netPrice.text = it.value.net_sales
                    Toast.makeText(requireContext(), "$it.value", Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
        //Sales Chart Test
        viewModel.salesBar.observe(viewLifecycleOwner) {
            binding.salesBarChart.data = it
            val time = arrayOf("1pm", "2pm", "3pm", "4pm")
            val xAxis = binding.salesBarChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(time)
            xAxis.setCenterAxisLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            binding.salesBarChart.isDragEnabled = true
            binding.salesBarChart.setVisibleXRangeMaximum(3f)

            val barSpace = 0f
            val groupSpace = 0.1f
            it.barWidth = 0.02f

            binding.salesBarChart.xAxis.axisMinimum = 0f
            binding.salesBarChart.xAxis.axisMaximum =
                0f + binding.salesBarChart.barData.getGroupWidth(groupSpace, barSpace) * 5

            binding.salesBarChart.axisLeft.axisMinimum = 0f
            binding.salesBarChart.groupBars(0f, groupSpace, barSpace)
            specs(binding.salesBarChart)
            binding.salesBarChart.invalidate()
        }

        //TransactionsChart Test
        viewModel.transactionsBar.observe(viewLifecycleOwner) {
            binding.transactionsBarChart.data = it
            val time = arrayOf("1pm", "2pm", "3pm", "4pm")
            val xAxis = binding.transactionsBarChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(time)
            xAxis.setCenterAxisLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            binding.transactionsBarChart.isDragEnabled = true
            binding.transactionsBarChart.setVisibleXRangeMaximum(3f)

            val barSpace = 0f
            val groupSpace = 0.1f
            it.barWidth = 0.02f

            binding.transactionsBarChart.xAxis.axisMinimum = 0f
            binding.transactionsBarChart.xAxis.axisMaximum =
                0f + binding.transactionsBarChart.barData.getGroupWidth(groupSpace, barSpace) * 5
            binding.transactionsBarChart.axisLeft.axisMinimum = 0f
            binding.transactionsBarChart.groupBars(0f, groupSpace, barSpace)
            specs(binding.transactionsBarChart)
            binding.transactionsBarChart.invalidate()
        }

        //PieChartTest
        viewModel.pieChart.observe(viewLifecycleOwner) {
            binding.pieChart.data = it
            binding.pieChart.transparentCircleRadius = 0F
            binding.pieChart.setDrawEntryLabels(false)
            binding.pieChart.holeRadius = 60F
            binding.pieChart.invalidate()
        }
    }

    private fun hideProgressBar() {
        binding.dashboardProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.dashboardProgress.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun getViewModel()= DashboardViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )   =     FragmentDashboardBinding.inflate(inflater, container, false)


    override fun getRepository(): DashBoardRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(DashboardApi::class.java, token)
//        val database = CategoryDatabase.invoke(requireContext())

        return DashBoardRepository(api)
    }
}