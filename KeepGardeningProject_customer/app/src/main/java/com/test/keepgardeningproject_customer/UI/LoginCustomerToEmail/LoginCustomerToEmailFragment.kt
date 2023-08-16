package com.test.keepgardeningproject_customer.UI.LoginCustomerToEmail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class LoginCustomerToEmailFragment : Fragment() {

    companion object {
        fun newInstance() = LoginCustomerToEmailFragment()
    }

    private lateinit var viewModel: LoginCustomerToEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_customer_to_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginCustomerToEmailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}