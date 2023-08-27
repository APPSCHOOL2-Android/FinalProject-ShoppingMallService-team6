package com.test.keepgardeningproject_seller.UI.HomeSellerMyPageMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerMyPageMainBinding

class HomeSellerMyPageMainFragment : Fragment() {

    lateinit var homeSellerMyPageMainBinding: FragmentHomeSellerMyPageMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeSellerMyPageMainBinding = FragmentHomeSellerMyPageMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        homeSellerMyPageMainBinding.run {



            textviewHomeSellerMyPageMainName.run {
                var newEmail = mainActivity.loginSellerInfo.userSellerEmail
                var newStorename = mainActivity.loginSellerInfo.userSellerStoreName
                UserRepository.getUserSellerInfoById(newEmail){
                    for(c1 in it.result.children){
                        var mynick = c1.child("userSellerNickname").value.toString()
                        textviewHomeSellerMyPageMainName.text = newStorename + " 상점의 " + mynick + " 님"
                    }
                }
            }

            textviewHomeSellerMyPageMainAuction.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_AUCTION_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainName.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainQna.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_QNA_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainReview.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_REVIEW_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainSellDelivery.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT,true,null)
            }
        }
        return homeSellerMyPageMainBinding.root
    }


}