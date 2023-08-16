package com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class StoreInfoCustomerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = StoreInfoCustomerDetailFragment()
    }

    private lateinit var viewModel: StoreInfoCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_info_customer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreInfoCustomerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}