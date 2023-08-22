package com.test.keepgardeningproject_seller.UI.LoginSellerMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerMainBinding

class LoginSellerMainFragment : Fragment() {

    lateinit var fragmentLoginSellerMainBinding: FragmentLoginSellerMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var loginSellerMainViewModel: LoginSellerMainViewModel

    lateinit var loginEmail:String
    lateinit var loginPw:String
    lateinit var nickname: String
    lateinit var loginType : String

    // 카카오톡 API불러오기
    val kakaoApi = KakaoAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoginSellerMainBinding = FragmentLoginSellerMainBinding.inflate(inflater)
        mainActivity = activity as  MainActivity



        fragmentLoginSellerMainBinding.run {
            // 카카오 로그인
            buttonLoginSellerMainKakaoLogin.setOnClickListener {
                // 카카오톡 로그인 호출
                kakaoApi.CheckLogin(requireContext(),mainActivity)
            }
            // 네이버 로그인
            buttonLoginSellerMainNaverLogin.setOnClickListener {
                Toast.makeText(requireContext(),"naver", Toast.LENGTH_SHORT).show()
            }
            // 구글 로그인
            buttonLoginSellerMainGoogleLogin.setOnClickListener {
                Toast.makeText(requireContext(),"google", Toast.LENGTH_SHORT).show()
            }
            // 이메일 로그인
            buttonLoginSellerMainEmailLogin.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT,true,null)
            }
        }



        return fragmentLoginSellerMainBinding.root
    }



}
