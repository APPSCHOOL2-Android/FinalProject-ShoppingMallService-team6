package com.test.keepgardeningproject_customer.UI.JoinCustomerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class JoinCustomerMainFragment : Fragment() {

    companion object {
        fun newInstance() = JoinCustomerMainFragment()
    }

    private lateinit var viewModel: JoinCustomerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_main_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JoinCustomerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}