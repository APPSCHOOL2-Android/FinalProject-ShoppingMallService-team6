package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.UserDAO
import com.test.keepgardeningproject_customer.DAO.UserInfo

import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.loginedUserInfo
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerModifyBinding
import kotlin.math.log

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
            var userNickName = loginedUserInfo.userNickname
            textInputEditTextMcNickName.setText(userNickName)
            toolbarMc.run {
                setTitle("정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                }
            }
            buttonMcModify.setOnClickListener {
                var userCustomerIdx = loginedUserInfo.userIdx
                var userCustomerLoginType = loginedUserInfo.userLoginType
                var userCustomerEmail = loginedUserInfo.userEmail
                var userCustomerPw = loginedUserInfo.userPw
                var userCustomerNickname = textInputEditTextMcNickName.text.toString()
                var User = UserInfo(userCustomerIdx,userCustomerLoginType,userCustomerEmail,userCustomerPw,userCustomerNickname)
                UserRepository.modifyUserInfo(User){
                    if(it.isSuccessful){
                        loginedUserInfo = User
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                    }
                }
            }
        }


        return  myPageCustomerModifyBinding.root
    }



}