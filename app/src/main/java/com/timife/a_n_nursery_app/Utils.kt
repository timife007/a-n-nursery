package com.timife.a_n_nursery_app

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.google.android.material.snackbar.Snackbar
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.login.ui.auth.LoginFragment

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

class Constants {
    companion object {
        const val QUERY_PAGE_SIZE = 15
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun specs(barChart: BarChart) {
    barChart.description.isEnabled = false
    barChart.axisLeft.setDrawGridLines(true)
    barChart.axisLeft.setDrawLabels(true)
    barChart.xAxis.setDrawGridLines(true)
    barChart.xAxis.setDrawLabels(false)
    barChart.setDrawGridBackground(false)
    barChart.axisRight.setDrawGridLines(false)
    barChart.axisRight.setDrawLabels(false)
    barChart.setDrawBorders(false)

}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()

}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                //@todo perform logout operation
                (this as BaseFragment<*, *, *>).logout()

            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}
