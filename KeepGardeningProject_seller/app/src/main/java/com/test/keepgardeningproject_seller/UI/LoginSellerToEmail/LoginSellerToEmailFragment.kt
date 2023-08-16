package com.test.keepgardeningproject_seller.UI.LoginSellerToEmail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class LoginSellerToEmailFragment : Fragment() {

    companion object {
        fun newInstance() = LoginSellerToEmailFragment()
    }

    private lateinit var viewModel: LoginSellerToEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_seller_to_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginSellerToEmailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}