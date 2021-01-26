package com.timife.a_n_nursery_app.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ravikoradiya.library.CenterTitle
import com.timife.a_n_nursery_app.databinding.FragmentDashboardBinding
import com.timife.a_n_nursery_app.login.ui.specs

class DashboardFragment : Fragment() {
    private lateinit var binding : FragmentDashboardBinding
//    private lateinit var navController: NavController

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding = FragmentDashboardBinding.inflate(inflater)
//        navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.dashboardToolbar.setupWithNavController(navController, appBarConfiguration)
//        CenterTitle.centerTitle(binding.dashboardToolbar,true)


        val spinnerItems = arrayOf("Today", "Yesterday", "Last Week")
        binding.spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,spinnerItems)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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



        //Sales Chart Test
        viewModel.salesBar.observe(viewLifecycleOwner,  {
            binding.salesBarChart.data = it
            val time = arrayOf("1pm","2pm","3pm","4pm")
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
            binding.salesBarChart.xAxis.axisMaximum = 0f+ binding.salesBarChart.barData.getGroupWidth(groupSpace,barSpace)*5

            binding.salesBarChart.axisLeft.axisMinimum = 0f
            binding.salesBarChart.groupBars(0f,groupSpace, barSpace)
            specs(binding.salesBarChart)
            binding.salesBarChart.invalidate()
        })

        //TransactionsChart Test
        viewModel.transactionsBar.observe(viewLifecycleOwner,{
            binding.transactionsBarChart.data = it
            val time = arrayOf("1pm","2pm","3pm","4pm")
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
            binding.transactionsBarChart.xAxis.axisMaximum = 0f+ binding.transactionsBarChart.barData.getGroupWidth(groupSpace,barSpace)*5

            binding.transactionsBarChart.axisLeft.axisMinimum = 0f
            binding.transactionsBarChart.groupBars(0f,groupSpace, barSpace)
            specs(binding.transactionsBarChart)


            binding.transactionsBarChart.invalidate()
        })

        //PieChartTest
        viewModel.pieChart.observe(viewLifecycleOwner,{
        binding.pieChart.data = it
            binding.pieChart.transparentCircleRadius = 0F
            binding.pieChart.setDrawEntryLabels(false)
            binding.pieChart.holeRadius = 60F
            binding.pieChart.invalidate()
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

}