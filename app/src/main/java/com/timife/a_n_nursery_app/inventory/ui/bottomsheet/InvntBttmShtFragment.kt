package com.timife.a_n_nursery_app.inventory.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.databinding.FragmentInventBttmShtBinding
import com.timife.a_n_nursery_app.inventory.BarcodeExtras

class InvntBttmShtFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentInventBttmShtBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        PRDownloader.initialize(requireContext())
        binding = FragmentInventBttmShtBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.barcodeProgress.visibility = View.GONE
        val product = InvntBttmShtFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = InvntBttmShtViewModelFactory(product, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(InvntBttmShtViewModel::class.java)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(InvntBttmShtViewModel::class.java)

        binding.editInventory.setOnClickListener {
            viewModel.displayProductEdit(product)
        }

        viewModel.navigateToEditProduct.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(
                InvntBttmShtFragmentDirections.actionInvntBttmShtFragmentToUpdateInventoryDialog(it)
            )
        })


        viewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
         //   Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            val barcodeUrl: String = it.barcode_url.toString()
            val fileName = """${it.name}.pdf"""
            binding.downloadBarcode.setOnClickListener {
                binding.barcodeProgress.visibility = View.VISIBLE
                downloadPdfFromInternet(
                    barcodeUrl,
                    BarcodeExtras.getRootDirPath(requireContext()),
                    fileName
                )
            }
        })
        return binding.root
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String?) {
        PRDownloader.download(
            url, dirPath, fileName
        ).build().start(object : OnDownloadListener {
            override fun onDownloadComplete() {
                binding.barcodeProgress.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "BarCode successfully downloaded!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(error: Error?) {
                binding.barcodeProgress.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Error in downloading file : $error",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}