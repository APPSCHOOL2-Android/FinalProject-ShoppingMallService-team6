package com.test.keepgardeningproject_seller.UI.ProductSellerRegister

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class ProductSellerRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSellerRegisterFragment()
    }

    private lateinit var viewModel: ProductSellerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_seller_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}