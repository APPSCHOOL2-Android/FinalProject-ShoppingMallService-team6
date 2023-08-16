package com.test.keepgardeningproject_customer.UI.HomeCustomerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class HomeCustomerMainFragment : Fragment() {

    companion object {
        fun newInstance() = HomeCustomerMainFragment()
    }

    private lateinit var viewModel: HomeCustomerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_customer_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeCustomerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}