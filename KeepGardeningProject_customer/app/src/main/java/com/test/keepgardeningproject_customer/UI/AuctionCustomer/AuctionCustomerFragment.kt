package com.test.keepgardeningproject_customer.UI.AuctionCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.R

class AuctionCustomerFragment : Fragment() {

    companion object {
        fun newInstance() = AuctionCustomerFragment()
    }

    private lateinit var viewModel: AuctionCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auction_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}