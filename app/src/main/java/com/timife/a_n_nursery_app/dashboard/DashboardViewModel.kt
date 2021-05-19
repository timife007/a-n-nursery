package com.timife.a_n_nursery_app.dashboard

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.dashboard.response.DashboardItems
import com.timife.a_n_nursery_app.dashboard.response.TransactionCategory
import com.timife.a_n_nursery_app.dashboard.response.TransactionPrice
import com.timife.a_n_nursery_app.dashboard.response.TransactionProduct
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) :
    BaseViewModel(dashBoardRepository) {
    private val _salesBar: MutableLiveData<BarData> = MutableLiveData()
    val salesBar: LiveData<BarData>
        get() = _salesBar

    private val _transactionsBar: MutableLiveData<BarData> = MutableLiveData()
    val transactionsBar: LiveData<BarData>
        get() = _transactionsBar

    private val _dashboard: MutableLiveData<Resource<DashboardItems>> = MutableLiveData()
    val dashboard: LiveData<Resource<DashboardItems>>
        get() = _dashboard

    private val _pieChart: MutableLiveData<PieData> = MutableLiveData()
    val pieChart: LiveData<PieData>
        get() = _pieChart

    init {
        processSalesChart()
        processProductChart()
        processCategoryPieChart()
    }

    private fun productsBarData(categoriesData: TransactionProduct) {
        val barOne: ArrayList<BarEntry> = ArrayList()
        val barTwo: ArrayList<BarEntry> = ArrayList()

        barOne.add(BarEntry(1.toFloat(), categoriesData.monday.lastweek.toFloat()))
        barTwo.add(BarEntry(1.toFloat(), categoriesData.monday.two_weeks.toFloat()))

        barOne.add(BarEntry(2.toFloat(), categoriesData.tuesday.lastweek.toFloat()))
        barTwo.add(BarEntry(2.toFloat(), categoriesData.tuesday.two_weeks.toFloat()))

        barOne.add(BarEntry(3.toFloat(), categoriesData.wednesday.lastweek.toFloat()))
        barTwo.add(BarEntry(3.toFloat(), categoriesData.wednesday.two_weeks.toFloat()))

        barOne.add(BarEntry(4.toFloat(), categoriesData.thursday.lastweek.toFloat()))
        barTwo.add(BarEntry(4.toFloat(), categoriesData.thursday.two_weeks.toFloat()))

        barOne.add(BarEntry(5.toFloat(), categoriesData.friday.lastweek.toFloat()))
        barTwo.add(BarEntry(5.toFloat(), categoriesData.friday.two_weeks.toFloat()))

        val set1 = BarDataSet(barOne, "barOne")
        set1.color = Color.parseColor("#50cf46")
        val set2 = BarDataSet(barTwo, "barTwo")
        set2.color = Color.parseColor("#eaccf6")

        set1.isHighlightEnabled = false
        set2.isHighlightEnabled = false

        set1.setDrawValues(false)
        set2.setDrawValues(false)

        val dataSets = ArrayList<IBarDataSet>()

        dataSets.add(set1)
        dataSets.add(set2)

        val data = BarData(dataSets)

        _transactionsBar.value = data

    }

    private fun salesBarData(salesData: TransactionPrice) {
        val barOne: ArrayList<BarEntry> = ArrayList()
        val barTwo: ArrayList<BarEntry> = ArrayList()

        barOne.add(BarEntry(1.toFloat(), salesData.monday.lastweek.toFloat()))
        barTwo.add(BarEntry(1.toFloat(), salesData.monday.two_weeks.toFloat()))

        barOne.add(BarEntry(2.toFloat(), salesData.tuesday.lastweek.toFloat()))
        barTwo.add(BarEntry(2.toFloat(), salesData.tuesday.two_weeks.toFloat()))

        barOne.add(BarEntry(3.toFloat(), salesData.wednesday.lastweek.toFloat()))
        barTwo.add(BarEntry(3.toFloat(), salesData.wednesday.two_weeks.toFloat()))

        barOne.add(BarEntry(4.toFloat(), salesData.thursday.lastweek.toFloat()))
        barTwo.add(BarEntry(4.toFloat(), salesData.thursday.two_weeks.toFloat()))

        barOne.add(BarEntry(5.toFloat(), salesData.friday.lastweek.toFloat()))
        barTwo.add(BarEntry(5.toFloat(), salesData.friday.two_weeks.toFloat()))

        val set1 = BarDataSet(barOne, "barOne")
        set1.color = Color.parseColor("#0b4317")
        val set2 = BarDataSet(barTwo, "barTwo")
        set2.color = Color.parseColor("#eaccf6")

        set1.isHighlightEnabled = false
        set2.isHighlightEnabled = false

        set1.setDrawValues(false)
        set2.setDrawValues(false)

        val dataSets = ArrayList<IBarDataSet>()

        dataSets.add(set1)
        dataSets.add(set2)

        val data = BarData(dataSets)

        _salesBar.value = data

    }

    private fun categoryBarData(pieData: TransactionCategory) {

        val colorClassArray = listOf(
            ColorTemplate.rgb("#274E24"),
            ColorTemplate.rgb("#50CF46"),
            ColorTemplate.rgb("#4E2433")
//            ColorTemplate.rgb("#3245F4"),
//            ColorTemplate.rgb("#CD801F")
        )

        val dataVals: ArrayList<PieEntry> = ArrayList()

        if(pieData.Plants != 0){
            dataVals.add(PieEntry(pieData.Plants.toFloat(), "Plants"))
        }
        if(pieData.Soil != 0){
            dataVals.add(PieEntry(pieData.Soil.toFloat(), "Soil"))
        }
        if(pieData.Trees != 0){
            dataVals.add(PieEntry(pieData.Trees.toFloat(), "Trees"))
        }

        val pieDataSet = PieDataSet(dataVals, "")
        pieDataSet.colors = colorClassArray
        val data = PieData(pieDataSet)
        _pieChart.value = data
    }

    fun getDashboard() = viewModelScope.launch {
        _dashboard.value = dashBoardRepository.getDashboard()
    }

     fun processSalesChart() = viewModelScope.launch {
        when (val entries = dashBoardRepository.getPriceChart()) {
            is Resource.Success -> {
                salesBarData(entries.value)
            }
            is Resource.Failure -> {

            }
            is Resource.Loading -> {

            }
        }
    }

    fun processProductChart() = viewModelScope.launch {
        when (val entries = dashBoardRepository.getProductChart()) {
            is Resource.Success -> {
                productsBarData(entries.value)
            }
            is Resource.Failure -> {

            }
            is Resource.Loading -> {

            }
        }
    }

    fun processCategoryPieChart() = viewModelScope.launch {
        when (val entries = dashBoardRepository.getCategoriesChart()) {
            is Resource.Success -> {
                categoryBarData(entries.value)
            }
            is Resource.Failure -> {

            }
            is Resource.Loading -> {

            }
        }
    }

}
