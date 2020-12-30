package com.timife.a_n_nursery_app.login.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.github.mikephil.charting.charts.BarChart

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}
fun specs(barChart: BarChart){
    barChart.description.isEnabled = false
    barChart.axisLeft.setDrawGridLines(true)
    barChart.axisLeft.setDrawLabels(false)
    barChart.xAxis.setDrawGridLines(true)
    barChart.xAxis.setDrawLabels(false)
    barChart.setDrawGridBackground(false)
    barChart.axisRight.setDrawGridLines(false)
    barChart.axisRight.setDrawLabels(false)
    barChart.setDrawBorders(false)
}