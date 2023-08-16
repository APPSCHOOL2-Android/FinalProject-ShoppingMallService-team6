package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class ProductCustomerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProductCustomerDetailFragment()
    }

    private lateinit var viewModel: ProductCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_customer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductCustomerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}