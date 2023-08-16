package com.test.keepgardeningproject_customer.UI.LoginCustomerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class LoginCustomerMainFragment : Fragment() {

    companion object {
        fun newInstance() = LoginCustomerMainFragment()
    }

    private lateinit var viewModel: LoginCustomerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_main_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginCustomerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}