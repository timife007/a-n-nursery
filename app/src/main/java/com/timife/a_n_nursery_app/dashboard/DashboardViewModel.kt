package com.timife.a_n_nursery_app.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.dashboard.response.DashboardItems
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) : BaseViewModel(dashBoardRepository) {
    private val _salesBar: MutableLiveData<BarData> = MutableLiveData()
    val salesBar: LiveData<BarData>
        get() = _salesBar

    private val _transactionsBar: MutableLiveData<BarData> = MutableLiveData()
    val transactionsBar: LiveData<BarData>
        get() = _transactionsBar

    private val _dashboard : MutableLiveData<Resource<DashboardItems>> = MutableLiveData()
    val dashboard : LiveData<Resource<DashboardItems>>
    get()= _dashboard

    private val _pieChart: MutableLiveData<PieData> = MutableLiveData()
    val pieChart: LiveData<PieData>
        get() = _pieChart

    init {
        salesBarData()
        transactionsBarData()
        pieDataEntry()
    }

    private fun transactionsBarData() {
        fun barEntries3(): ArrayList<BarEntry> {
            val barEntries: ArrayList<BarEntry> = ArrayList()
            barEntries.add(BarEntry(1f, 6F))
            barEntries.add(BarEntry(2f, 2.5F))
            barEntries.add(BarEntry(3f, 5.5F))
            barEntries.add(BarEntry(4f, 2.5F))
            return barEntries
        }

        fun barEntries4(): ArrayList<BarEntry> {
            val barEntries: ArrayList<BarEntry> = ArrayList()
            barEntries.add(BarEntry(1f, 3F))
            barEntries.add(BarEntry(2f, 3.5F))
            barEntries.add(BarEntry(3f, 1.5F))
            barEntries.add(BarEntry(4f, 5.8F))
            return barEntries
        }

        val barDataSet1 = BarDataSet(barEntries3(), "DataSet 1")
        barDataSet1.color = ColorTemplate.rgb("#50CF46")
        val barDataSet2 = BarDataSet(barEntries4(), "DataSet 2")
        barDataSet2.color = ColorTemplate.rgb("#b19cd9")
        val data = BarData(barDataSet1, barDataSet2)
        _transactionsBar.value = data

    }


    private fun salesBarData() {
        fun barEntries1(): ArrayList<BarEntry> {
            val barEntries: ArrayList<BarEntry> = ArrayList()
            barEntries.add(BarEntry(1f, 200F))
            barEntries.add(BarEntry(2f, 560F))
            barEntries.add(BarEntry(3f, 400F))
            barEntries.add(BarEntry(4f, 180F))
            return barEntries
        }

        fun barEntries2(): ArrayList<BarEntry> {
            val barEntries: ArrayList<BarEntry> = ArrayList()
            barEntries.add(BarEntry(1f, 180F))
            barEntries.add(BarEntry(2f, 190F))
            barEntries.add(BarEntry(3f, 140F))
            barEntries.add(BarEntry(4f, 200F))
            return barEntries
        }

        val barDataSet1 = BarDataSet(barEntries1(), "DataSet 1")
        barDataSet1.color = ColorTemplate.rgb("#0B4317")
        val barDataSet2 = BarDataSet(barEntries2(), "DataSet 2")
        barDataSet2.color = ColorTemplate.rgb("#CD801F")
        val data = BarData(barDataSet1, barDataSet2)
        _salesBar.value = data

    }

    private fun pieDataEntry(): ArrayList<PieEntry> {
        val colorClassArray = listOf(
            ColorTemplate.rgb("#4E2433"),
            ColorTemplate.rgb("#274E24"),
            ColorTemplate.rgb("#50CF46"),
            ColorTemplate.rgb("#3245F4"),
            ColorTemplate.rgb("#CD801F")
        )

        val dataVals: ArrayList<PieEntry> = ArrayList()
        dataVals.add(PieEntry(10F, "Ecru Lab"))
        dataVals.add(PieEntry(7F, "Yu"))
        dataVals.add(PieEntry(3F, "Cutty"))
        dataVals.add(PieEntry(15F, "Hyun"))
        dataVals.add(PieEntry(10F, "Kinp"))
        val pieDataSet = PieDataSet(dataVals, "Series PieChart")
        pieDataSet.colors = colorClassArray
        val pieData = PieData(pieDataSet)
        _pieChart.value = pieData
        return dataVals

    }

     fun getDashboard() = viewModelScope.launch {
        _dashboard.value = dashBoardRepository.getDashboard()
    }
}