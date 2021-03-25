package com.timife.a_n_nursery_app.inventory.ui.bottomsheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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
        val product = InvntBttmShtFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = InvntBttmShtViewModelFactory(product, application)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(InvntBttmShtViewModel::class.java)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(InvntBttmShtViewModel::class.java)

       viewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
             val barcodeUrl = it.barcode_url
             binding.downloadBarcode.setOnClickListener {
                 binding.barcodeProgress.visibility = View.VISIBLE
                 downloadPdfFromInternet(barcodeUrl!!,BarcodeExtras.getRootDirPath(requireContext()),"")
             }
         })
        return binding.root
    }

    private fun downloadPdfFromInternet(url:String,dirPath:String,fileName:String?){
        PRDownloader.download(
            url,dirPath,fileName
        ).build().start(object : OnDownloadListener{
            override fun onDownloadComplete() {
                Toast.makeText(requireContext(),"BarCode successfully downloaded!",Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Error?) {
                Toast.makeText(requireContext(),"Error in downloading file : $error",Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }
}