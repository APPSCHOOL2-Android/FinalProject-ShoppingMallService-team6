package com.test.keepgardeningproject_customer.UI.StoreInfoCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class StoreInfoCustomerFragment : Fragment() {

    companion object {
        fun newInstance() = StoreInfoCustomerFragment()
    }

    private lateinit var viewModel: StoreInfoCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_info_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreInfoCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}