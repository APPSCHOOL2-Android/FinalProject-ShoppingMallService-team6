package com.test.keepgardeningproject_seller.UI.AuctionSellerEdit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerEditBinding
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerEditBinding

class AuctionSellerEditFragment : Fragment() {

    lateinit var fragmentAuctionSellerEditBinding: FragmentAuctionSellerEditBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerEditFragment()
    }

    private lateinit var viewModel: AuctionSellerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerEditBinding = FragmentAuctionSellerEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerEditBinding.run {
            buttonAuctionSellerEditEdit.setOnClickListener {
                mainActivity.removeFragment(AUCTION_SELLER_EDIT_FRAGMENT)
            }
        }
        return fragmentAuctionSellerEditBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}