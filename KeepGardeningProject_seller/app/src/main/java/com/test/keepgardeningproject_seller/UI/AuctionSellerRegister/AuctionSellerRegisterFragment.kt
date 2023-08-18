package com.test.keepgardeningproject_seller.UI.AuctionSellerRegister

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerRegisterBinding

class AuctionSellerRegisterFragment : Fragment() {

    lateinit var fragmentAuctionSellerRegisterBinding: FragmentAuctionSellerRegisterBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerRegisterFragment()
    }

    private lateinit var viewModel: AuctionSellerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerRegisterBinding = FragmentAuctionSellerRegisterBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerRegisterBinding.run {
            buttonAuctionSellerRegisterRegister.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setIcon(R.drawable.ic_warning_24px)
                builder.setTitle("경고")
                builder.setMessage("경매가 시작되면 \n경매가격, 마감날짜를 \n수정하실 수 없습니다.")
                builder.setNegativeButton("취소",null)
                builder.setPositiveButton("등록") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.replaceFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT,true,null)
                }
                builder.show()
            }
        }
        return fragmentAuctionSellerRegisterBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}