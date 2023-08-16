package com.test.keepgardeningproject_customer.UI.LoginCustomerFindPw

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class LoginCustomerFindPwFragment : Fragment() {

    companion object {
        fun newInstance() = LoginCustomerFindPwFragment()
    }

    private lateinit var viewModel: LoginCustomerFindPwViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_customer_find_pw, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginCustomerFindPwViewModel::class.java)
        // TODO: Use the ViewModel
    }

}