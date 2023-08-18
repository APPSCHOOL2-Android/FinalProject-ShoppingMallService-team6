package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerModifyBinding

class MyPageCustomerModifyFragment : Fragment() {

    lateinit var myPageCustomerModifyBinding: FragmentMyPageCustomerModifyBinding
    lateinit var myPageCustomerModifyViewModel: MyPageCustomerModifyViewModel
    lateinit var mainActivity :MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageCustomerModifyBinding = FragmentMyPageCustomerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        myPageCustomerModifyBinding.run {
            toolbarMc.run {
                setTitle("정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)

                }
            }
            buttonMcModify.run {
                setOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                }
            }
        }

       return  myPageCustomerModifyBinding.root
    }



}