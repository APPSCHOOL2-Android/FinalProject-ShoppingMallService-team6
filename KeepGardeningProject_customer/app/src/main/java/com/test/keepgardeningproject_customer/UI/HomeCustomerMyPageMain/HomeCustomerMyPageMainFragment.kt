package com.test.keepgardeningproject_customer.UI.HomeCustomerMyPageMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.ktx.values
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.UI.HomeCustomerMain.HomeCustomerMainFragment
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMyPageMainBinding

class HomeCustomerMyPageMainFragment : Fragment() {
    lateinit var fragmentHomeCustomerMyPageMainBinding: FragmentHomeCustomerMyPageMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var homeCustomerMainFragment: HomeCustomerMainFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeCustomerMyPageMainBinding = FragmentHomeCustomerMyPageMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        homeCustomerMainFragment = HomeCustomerMainFragment()
        homeCustomerMainFragment.mainActivity = activity as MainActivity
        fragmentHomeCustomerMyPageMainBinding.run {

            textviewHomeCustomerMyPageMainName.run {

                var newemail = MainActivity.loginedUserInfo.userEmail.toString()
                UserRepository.getUserInfoById(newemail){
                    for(a1 in it.result.children){
                        var mynick = a1.child("userNickname").value
                        textviewHomeCustomerMyPageMainName.text = mynick.toString() + "님"
                    }
                }

                setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT,true,null)
                }
            }

            textviewHomeCustomerMyPageMainAuction.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_AUCTION_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainPurchase.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainQna.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainReview.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainWish.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_WISH_FRAGMENT,true,null)
            }
            // 로그아웃 버튼
            buttonHomeCustomerMyPageMainLogOut.setOnClickListener {
                MainActivity.isLogined = false
                MainActivity.loginedUserInfo = UserInfo(null,null,null,null,null)
                mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_MY_PAGE_MAIN)
            }
            // 회원탈퇴 버튼
            buttonHomeCustomerMyPageMainWithdrawal.setOnClickListener {
                MainActivity.isLogined = false
                MainActivity.loginedUserInfo = UserInfo(null,null,null,null,null)
                mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_MY_PAGE_MAIN)
            }
        }
        return fragmentHomeCustomerMyPageMainBinding.root
    }

    override fun onResume() {
        super.onResume()

    }



}