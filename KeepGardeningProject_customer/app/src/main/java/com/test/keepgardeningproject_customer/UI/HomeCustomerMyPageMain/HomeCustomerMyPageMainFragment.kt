package com.test.keepgardeningproject_customer.UI.HomeCustomerMyPageMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
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
            textviewHomeCustomerMyPageMainName.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT,true,null)
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
            buttonHomeCustomerMyPageMainLogOut.setOnClickListener {
                homeCustomerMainFragment.replaceFragment(HomeCustomerMainFragment.HOME_CUSTOMER_MAIN_HOME,false,null)
            }
            buttonHomeCustomerMyPageMainWithdrawal.setOnClickListener {
                homeCustomerMainFragment.replaceFragment(HomeCustomerMainFragment.HOME_CUSTOMER_MAIN_HOME,false,null)
            }
        }
        return fragmentHomeCustomerMyPageMainBinding.root
    }



}