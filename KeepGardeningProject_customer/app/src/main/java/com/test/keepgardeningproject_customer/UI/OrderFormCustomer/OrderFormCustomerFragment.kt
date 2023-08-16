package com.test.keepgardeningproject_customer.UI.OrderFormCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class OrderFormCustomerFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFormCustomerFragment()
    }

    private lateinit var viewModel: OrderFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_form_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderFormCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}