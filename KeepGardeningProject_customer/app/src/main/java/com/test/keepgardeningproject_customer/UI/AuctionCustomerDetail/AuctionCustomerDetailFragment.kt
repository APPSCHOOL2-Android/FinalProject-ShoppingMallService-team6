package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class AuctionCustomerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = AuctionCustomerDetailFragment()
    }

    private lateinit var viewModel: AuctionCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auction_customer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionCustomerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}