package com.test.keepgardeningproject_seller.UI.AuctionSellerQnA

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class AuctionSellerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = AuctionSellerQnAFragment()
    }

    private lateinit var viewModel: AuctionSellerQnAViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auction_seller_qn_a, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerQnAViewModel::class.java)
        // TODO: Use the ViewModel
    }

}