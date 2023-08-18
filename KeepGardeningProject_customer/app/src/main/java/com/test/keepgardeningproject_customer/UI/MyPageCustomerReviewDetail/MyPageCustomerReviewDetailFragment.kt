package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerAuctionBinding

class MyPageCustomerReviewDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewDetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerReviewDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_customer_review_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }


}