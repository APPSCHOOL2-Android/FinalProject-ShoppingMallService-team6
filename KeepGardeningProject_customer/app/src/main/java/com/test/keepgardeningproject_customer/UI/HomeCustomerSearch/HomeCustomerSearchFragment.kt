package com.test.keepgardeningproject_customer.UI.HomeCustomerSearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class HomeCustomerSearchFragment : Fragment() {

    companion object {
        fun newInstance() = HomeCustomerSearchFragment()
    }

    private lateinit var viewModel: HomeCustomerSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_customer_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeCustomerSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}