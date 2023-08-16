package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class MyPageCustomerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerQnADetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_customer_qn_a_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}