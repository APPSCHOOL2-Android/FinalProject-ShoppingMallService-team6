package com.test.keepgardeningproject_seller.UI.ProductSellerEdit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class ProductSellerEditFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSellerEditFragment()
    }

    private lateinit var viewModel: ProductSellerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_seller_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}