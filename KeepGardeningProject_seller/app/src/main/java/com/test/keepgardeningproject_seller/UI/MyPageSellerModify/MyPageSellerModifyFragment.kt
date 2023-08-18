package com.test.keepgardeningproject_seller.UI.MyPageSellerModify

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerModifyBinding

class MyPageSellerModifyFragment : Fragment() {

    lateinit var myPageSellerModifyBinding: FragmentMyPageSellerModifyBinding
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MyPageSellerModifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       myPageSellerModifyBinding = FragmentMyPageSellerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        myPageSellerModifyBinding.run {
            toolbarMs.run {
                setTitle("정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                }
            }
            buttonMsModifyEnd.run {
                setOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                }
            }
        }

        return myPageSellerModifyBinding.root
    }



}