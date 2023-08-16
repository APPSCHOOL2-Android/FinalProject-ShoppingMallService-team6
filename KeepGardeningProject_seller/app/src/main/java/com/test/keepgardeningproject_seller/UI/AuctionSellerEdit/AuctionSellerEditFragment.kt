package com.test.keepgardeningproject_seller.UI.AuctionSellerEdit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class AuctionSellerEditFragment : Fragment() {

    companion object {
        fun newInstance() = AuctionSellerEditFragment()
    }

    private lateinit var viewModel: AuctionSellerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auction_seller_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}