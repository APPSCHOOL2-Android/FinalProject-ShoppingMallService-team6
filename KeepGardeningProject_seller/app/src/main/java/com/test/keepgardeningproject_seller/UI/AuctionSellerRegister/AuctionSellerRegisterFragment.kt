package com.test.keepgardeningproject_seller.UI.AuctionSellerRegister

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class AuctionSellerRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = AuctionSellerRegisterFragment()
    }

    private lateinit var viewModel: AuctionSellerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auction_seller_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}