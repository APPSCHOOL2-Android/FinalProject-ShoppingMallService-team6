package com.test.keepgardeningproject_seller.UI.AuctionSellerEdit

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
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

            toolbarAuctionSellerEdit.run {
                title = "경매 수정"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_SELLER_EDIT_FRAGMENT)
                }
            }
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