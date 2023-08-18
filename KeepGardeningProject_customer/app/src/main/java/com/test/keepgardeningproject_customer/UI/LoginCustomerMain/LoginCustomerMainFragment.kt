package com.test.keepgardeningproject_customer.UI.LoginCustomerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentLoginCustomerMainBinding

class LoginCustomerMainFragment : Fragment() {

    lateinit var fragmentLoginCustomerMainBinding:FragmentLoginCustomerMainBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginCustomerMainBinding = FragmentLoginCustomerMainBinding.inflate(inflater)
        mainActivity = activity as  MainActivity

        fragmentLoginCustomerMainBinding.run {
            toolbarLoginCustomerMain.run {
                setTitle("로그인")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT)
                }
            }
            // 카카오 로그인
            buttonLoginCustomerMainKakaoLogin.setOnClickListener {
                Toast.makeText(requireContext(),"kakao",Toast.LENGTH_SHORT).show()
            }
            // 네이버 로그인
            buttonLoginCustomerMainNaverLogin.setOnClickListener {
                Toast.makeText(requireContext(),"naver",Toast.LENGTH_SHORT).show()
            }
            // 구글 로그인
            buttonLoginCustomerMainGoogleLogin.setOnClickListener {
                Toast.makeText(requireContext(),"google",Toast.LENGTH_SHORT).show()
            }
            // 이메일 로그인
            buttonLoginCustomerMainEmailLogin.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT,true,null)
            }
        }

        return fragmentLoginCustomerMainBinding.root
    }



}