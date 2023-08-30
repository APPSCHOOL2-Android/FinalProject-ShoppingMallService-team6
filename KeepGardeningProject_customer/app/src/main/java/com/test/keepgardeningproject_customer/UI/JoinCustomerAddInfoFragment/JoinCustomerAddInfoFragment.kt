package com.test.keepgardeningproject_customer.UI.JoinCustomerAddInfoFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentJoinCustomerAddInfoBinding


class JoinCustomerAddInfoFragment : Fragment() {
    lateinit var fragmentJoinCustomerAddInfoBinding: FragmentJoinCustomerAddInfoBinding
    lateinit var mainActivity: MainActivity
    var userType:Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentJoinCustomerAddInfoBinding = FragmentJoinCustomerAddInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        // Inflate the layout for this fragment

        fragmentJoinCustomerAddInfoBinding.run {
            var joinUserType = arguments?.getLong("joinUserType")
            var joinUserEmail = arguments?.getString("joinUserEmail")
            if (joinUserType != null) {
                userType = joinUserType
            }
            textInputEditTextJoinCustomerAddInfoEmail.setText(joinUserEmail)
            Log.e("아앙","$joinUserEmail")
            buttonJoinCustomerAddInfoJoin.setOnClickListener {
                userSubmit()
                mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT,false,null)
            }
        }
        return fragmentJoinCustomerAddInfoBinding.root
    }
    fun userSubmit(){
        fragmentJoinCustomerAddInfoBinding.run {
            var email = textInputEditTextJoinCustomerAddInfoEmail.text.toString()
            val pw = "None"
            val nickname = textInputEditTextJoinCustomerAddInfoNickName.text.toString()
            UserRepository.getUserIndex {
                var userIndex = it.result.value as Long
                userIndex++

                val userinfo = UserInfo(userIndex,userType,email,pw,nickname)
                UserRepository.setUserInfo(userinfo){
                    UserRepository.setUserIdx(userIndex){
                        if(it.isSuccessful){
                            mainActivity.removeFragment(MainActivity.JOIN_CUSTOMER_ADD_INFO_FRAGMENT)
                        }
                    }
                }
            }
        }
    }


}