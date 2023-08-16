package com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class OrderCheckFormCustomerFragment : Fragment() {

    companion object {
        fun newInstance() = OrderCheckFormCustomerFragment()
    }

    private lateinit var viewModel: OrderCheckFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_check_form_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderCheckFormCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}