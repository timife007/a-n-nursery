package com.timife.a_n_nursery_app.sales

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timife.a_n_nursery_app.R

class TerminalFragment : Fragment() {

    companion object {
        fun newInstance() = TerminalFragment()
    }

    private lateinit var viewModel: TerminalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terminal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}