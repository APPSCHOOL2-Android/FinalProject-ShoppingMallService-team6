package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.loginedUserInfo
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerModifyBinding


class MyPageCustomerModifyFragment : Fragment() {

    lateinit var myPageCustomerModifyBinding: FragmentMyPageCustomerModifyBinding
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