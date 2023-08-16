package com.test.keepgardeningproject_seller.UI.ProductSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class ProductSellerReviewFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSellerReviewFragment()
    }

    private lateinit var viewModel: ProductSellerReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_seller_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}