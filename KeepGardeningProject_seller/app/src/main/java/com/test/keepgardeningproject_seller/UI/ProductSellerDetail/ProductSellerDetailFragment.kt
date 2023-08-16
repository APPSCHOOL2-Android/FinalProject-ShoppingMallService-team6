package com.test.keepgardeningproject_seller.UI.ProductSellerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class ProductSellerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSellerDetailFragment()
    }

    private lateinit var viewModel: ProductSellerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_seller_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}