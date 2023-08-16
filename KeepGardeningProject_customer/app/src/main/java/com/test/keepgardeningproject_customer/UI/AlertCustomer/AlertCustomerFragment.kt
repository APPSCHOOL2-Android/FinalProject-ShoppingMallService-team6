package com.test.keepgardeningproject_customer.UI.AlertCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class AlertCustomerFragment : Fragment() {

    companion object {
        fun newInstance() = AlertCustomerFragment()
    }

    private lateinit var viewModel: AlertCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alert_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlertCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}