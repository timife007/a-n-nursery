package com.timife.a_n_nursery_app.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.dashboard.network.DashboardApi
import com.timife.a_n_nursery_app.databinding.FragmentDashboardBinding
import com.timife.a_n_nursery_app.handleApiError
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat


class DashboardFragment :
    BaseFragment<DashboardViewModel, FragmentDashboardBinding, DashBoardRepository>() {
    var swipeCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "DashBoard"
        viewModel.getDashboard()

//        val spinnerItems = arrayOf("Today", "Yesterday", "Last Week")
//        binding.spinner.adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_dropdown_item_1line,
//            spinnerItems
//        )
//
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                spinnerItems.get(position)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }

        fun refresh(){
            viewModel.processSalesChart()
            viewModel.processProductChart()
            viewModel.processCategoryPieChart()
        }

        binding.swipeRefreshDashboard!!.setOnRefreshListener {
            swipeCount += 1


            if (swipeCount > 0) {
                refresh()
            }
            refresh()

            binding.swipeRefreshDashboard!!.isRefreshing = false
        }


        viewModel.dashboard.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.inventoryProdNum.text = it.value.low_inventory.toString()
                    binding.productNumber.text = it.value.products_sold.toString()
                    binding.transactionNumber.text = it.value.transactions.toString()
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
            binding.salesBarChart.setDrawBarShadow(false)
            binding.salesBarChart.description.isEnabled = false
            binding.salesBarChart.setPinchZoom(false)
            binding.salesBarChart.setDrawGridBackground(true)
            // empty labels so that the names are spread evenly
            // empty labels so that the names are spread evenly
            val labels =
                arrayOf("", "Mon", "Tue", "Wed", "Thur", "Fri", "")
            val xAxis: XAxis = binding.salesBarChart.xAxis
            xAxis.setCenterAxisLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.granularity = 1f // only intervals of 1 day

            xAxis.textColor = Color.BLACK
            xAxis.textSize = 12f
            xAxis.axisLineColor = Color.WHITE
            xAxis.axisMinimum = 1f
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            val leftAxis: YAxis = binding.salesBarChart.axisLeft
            leftAxis.textColor = Color.BLACK
            leftAxis.textSize = 12f
            leftAxis.axisLineColor = Color.WHITE
            leftAxis.setDrawGridLines(true)
            leftAxis.granularity = 2f
            leftAxis.setLabelCount(8, true)
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.labelCount = 6
            leftAxis.axisMinimum = 20f
            leftAxis.valueFormatter = MyValueFormatter()
            binding.salesBarChart.axisRight.isEnabled = false
            binding.salesBarChart.legend.isEnabled = false

            val groupSpace = 0.4f
            val barSpace = 0f
            val barWidth = 0.3f

            it.barWidth = barWidth

            xAxis.axisMaximum = labels.size - 1.1f
            binding.salesBarChart.data = it
            binding.salesBarChart.setScaleEnabled(false)
            binding.salesBarChart.setVisibleXRangeMaximum(6f)
            binding.salesBarChart.setExtraOffsets(5F, 5F, 5F, 5F);
            binding.salesBarChart.groupBars(1f, groupSpace, barSpace)
            binding.salesBarChart.invalidate()
        }

        //TransactionsChart Test
        viewModel.transactionsBar.observe(viewLifecycleOwner) {
            binding.transactionsBarChart.setDrawBarShadow(false)
            binding.transactionsBarChart.description.isEnabled = false
            binding.transactionsBarChart.setPinchZoom(false)
            binding.transactionsBarChart.setDrawGridBackground(true)

            val labels =
                arrayOf("", "Mon", "Tue", "Wed", "Thur", "Fri", "")
            val xAxis: XAxis = binding.transactionsBarChart.xAxis
            xAxis.setCenterAxisLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.granularity = 1f // only intervals of 1 day

            xAxis.textColor = Color.BLACK
            xAxis.textSize = 12f
            xAxis.axisLineColor = Color.WHITE
            xAxis.axisMinimum = 1f
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            val leftAxis: YAxis = binding.transactionsBarChart.axisLeft
            leftAxis.textColor = Color.BLACK
            leftAxis.textSize = 12f
            leftAxis.axisLineColor = Color.WHITE
            leftAxis.setDrawGridLines(true)
            leftAxis.granularity = 2f
            leftAxis.setLabelCount(8, true)
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.labelCount = 6
            leftAxis.axisMinimum = 20f
            binding.transactionsBarChart.axisRight.isEnabled = false
            binding.transactionsBarChart.legend.isEnabled = false

            val groupSpace = 0.4f
            val barSpace = 0f
            val barWidth = 0.3f

            it.barWidth = barWidth

            xAxis.axisMaximum = labels.size - 1.1f
            binding.transactionsBarChart.data = it
            binding.transactionsBarChart.setScaleEnabled(false)
            binding.transactionsBarChart.setVisibleXRangeMaximum(6f)
            binding.transactionsBarChart.groupBars(1f, groupSpace, barSpace)
            binding.transactionsBarChart.setExtraOffsets(5F, 5F, 5F, 5F);
            binding.transactionsBarChart.invalidate()
        }

        //PieChartTest
        viewModel.pieChart.observe(viewLifecycleOwner) {
            binding.pieChart.description.isEnabled = false
            binding.pieChart.data = it
            binding.pieChart.transparentCircleRadius = 0F
            binding.pieChart.setDrawEntryLabels(false)
            binding.pieChart.holeRadius = 60F
            binding.pieChart.invalidate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.activities_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.activities) {
            this.findNavController().navigate(R.id.action_dashboardFragment_to_activitiesFragment)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun hideProgressBar() {
        binding.dashboardProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.dashboardProgress.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
    }

    override fun getViewModel() = DashboardViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)


    override fun getRepository(): DashBoardRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(DashboardApi::class.java, token)
        return DashBoardRepository(api)
    }

    class MyValueFormatter : ValueFormatter() {
        private val format = DecimalFormat("###,##0.0")

        // override this for e.g. LineChart or ScatterChart
        override fun getPointLabel(entry: Entry?): String {
            return format.format(entry?.y)
        }

        // override this for BarChart
        override fun getBarLabel(barEntry: BarEntry?): String {
            return format.format(barEntry?.y)
        }

        // override this for custom formatting of XAxis or YAxis labels
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return "$" + value.toInt().toString()
        }
    }

}