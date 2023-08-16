package com.test.keepgardeningproject_seller.UI.ProductSellerQnA

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class ProductSellerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSellerQnAFragment()
    }

    private lateinit var viewModel: ProductSellerQnAViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_seller_qn_a, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerQnAViewModel::class.java)
        // TODO: Use the ViewModel
    }

}