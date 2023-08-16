package com.test.keepgardeningproject_customer.UI.MyPageCustomerWish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class MyPageCustomerWishFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerWishFragment()
    }

    private lateinit var viewModel: MyPageCustomerWishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_customer_wish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerWishViewModel::class.java)
        // TODO: Use the ViewModel
    }

}