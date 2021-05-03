package com.timife.a_n_nursery_app.sales

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseFragment
import com.timife.a_n_nursery_app.databinding.FragmentSalesBinding
import com.timife.a_n_nursery_app.enable
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationApi
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


private const val CAMERA_REQUEST_CODE = 101

class SalesFragment : BaseFragment<SalesViewModel, FragmentSalesBinding, SalesRepository>() {
    private lateinit var codeScanner: CodeScanner

//    private lateinit var viewModel: SalesViewModel

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setUpPermissions()
//        setHasOptionsMenu(true)
//
////        return inflater.inflate(R.layout.fragment_sales, container, false)
//    }

    private fun setUpPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        requireContext(), "You need the camera permission to be able to scan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPermissions()
//        binding

        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    val scanText = view.findViewById<TextView>(R.id.scan_text)
                    scanText.text = it.text
                    if (scanText.text != null) {
                        viewModel.searchByBarcode(scanText.text.toString())
                        viewModel.barcodeItem.observe(viewLifecycleOwner, Observer {
                            when (it) {
                                is Resource.Success -> {
//                                        binding.fetchButton.enable(true)
                                    binding.salesProgress.visibility = View.GONE
                                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT)
                                        .show()
                                    val inventory = Inventory(
                                        it.value.results[0].barcode_digit,
                                        it.value.results[0].barcode_url,
                                        it.value.results[0].botanical_name,
                                        it.value.results[0].category,
                                        it.value.results[0].classification,
                                        it.value.results[0].color,
                                        it.value.results[0].cost,
                                        it.value.results[0].created,
                                        it.value.results[0].id,
                                        it.value.results[0].image,
                                        it.value.results[0].location,
                                        it.value.results[0].lot,
                                        it.value.results[0].name,
                                        it.value.results[0].price,
                                        it.value.results[0].quantity,
                                        it.value.results[0].size,
                                        it.value.results[0].updated
                                    )
                                    this@SalesFragment.findNavController().navigate(
                                        SalesFragmentDirections.actionSalesFragmentToSalesBttmShtFragment(
                                            inventory
                                        )
                                    )
                                }

                                is Resource.Failure -> {
                                    binding.salesProgress.visibility = View.GONE
                                    Toast.makeText(
                                        requireContext(),
                                        "Unable to fetch scanned item",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is Resource.Loading ->
                                    binding.salesProgress.visibility = View.VISIBLE
                            }
                        })
                        viewModel.navigateToScannedItem.observe(viewLifecycleOwner, Observer {
                            val scannedProduct = it
                            binding.fetchButton.setOnClickListener {
                                viewModel.displayScannedItem(scannedProduct)
                            }
                        })
                    }
                }
            }

            errorCallback = ErrorCallback {
                Log.e("Main", "Camera initialization error:${it.message}")
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SalesViewModel::class.java)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sales_menu, menu)
    }

    override fun getViewModel() = SalesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSalesBinding.inflate(inflater)

    override fun getRepository(): SalesRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(SalesApi::class.java, token)
//        val database = CategoryDatabase.invoke(requireContext())

        return SalesRepository(api)
    }

}