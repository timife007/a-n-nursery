package com.timife.a_n_nursery_app.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timife.a_n_nursery_app.R
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.databinding.SalesBttmShtFragmentBinding
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import com.timife.a_n_nursery_app.sales.cart.CartDatabase
import com.timife.a_n_nursery_app.sales.cart.CartItem
import com.timife.a_n_nursery_app.sales.network.SalesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SalesBttmShtFragment : BottomSheetDialogFragment() {
    private lateinit var binding: SalesBttmShtFragmentBinding
    private lateinit var repository: SalesRepository
    protected val retrofitClient = RetrofitClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(SalesApi::class.java, token)
        val database = CartDatabase.invoke(requireContext())
        repository = SalesRepository(api, database)
        val application = requireNotNull(activity).application
        binding = SalesBttmShtFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val product = SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct
        val productPrice =
            SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct.price
        val viewModelFactory = SalesViewModelFactory(product, application,repository)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory).get(SalesBttmShtViewModel::class.java)
        binding.itemPrice.text = productPrice

        val productName = SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct.name
        val productSize =
            SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct.size
        val productQuantity =
            SalesBttmShtFragmentArgs.fromBundle(requireArguments()).scannedProduct.quantity

        binding.addCartBtn.setOnClickListener {
            val cartItem = CartItem(
                productName,productSize,productQuantity,productPrice
            )
            binding.viewModel.upsert(cartItem)
            dismiss()
        }

        return binding.root
    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogFragment
    }

}