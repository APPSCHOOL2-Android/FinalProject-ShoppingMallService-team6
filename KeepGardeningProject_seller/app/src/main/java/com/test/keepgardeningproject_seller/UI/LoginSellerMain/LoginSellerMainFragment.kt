package com.test.keepgardeningproject_seller.UI.LoginSellerMain

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.API.NaverAPI
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.UserRepository.Companion.getUserSellerInfoById
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerMainBinding

class LoginSellerMainFragment : Fragment() {

    lateinit var fragmentLoginSellerMainBinding: FragmentLoginSellerMainBinding
    lateinit var mainActivity: MainActivity

    // 카카오톡 API 불러오기
    val kakaoApi = KakaoAPI()
    // 네이버 API 불러오기
    val naverApi = NaverAPI()
    // val sharedPref = requireContext().getSharedPreferences("myLogin", Context.MODE_PRIVATE)
    var user_email : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 네이버 로그인 SDK 초기화
        NaverIdLoginSDK.initialize(requireContext(), "el7gHNDTrFweYrwQdjFT", "Kp1DmQI6qv", "킵 가드닝")
        fragmentLoginSellerMainBinding = FragmentLoginSellerMainBinding.inflate(inflater)
        mainActivity = activity as  MainActivity

//        val editor = sharedPref.edit()
//        editor.putString("user_email", user_email)
//        editor.putBoolean("is_logged_in", true)
//        editor.apply()

        fragmentLoginSellerMainBinding.run {
            // 카카오 로그인
            buttonLoginSellerMainKakaoLogin.setOnClickListener {
                kakaoApi.checkKaKaoLogin(requireContext())
                kakaoLogin()
            }
            // 네이버 로그인
            buttonLoginSellerMainNaverLogin.setOnClickListener {
                Toast.makeText(requireContext(),"naver", Toast.LENGTH_SHORT).show()
                //naverApi.checkNaverLogin(requireContext())
                naverLogin()
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
    fun kakaoLogin(){
        // 로그인 정보 받아오기

        UserApiClient.instance.me { user, error ->
            if (error != null) {
            } else if (user != null) {
                var email = user.kakaoAccount?.email
                if (email != null) {
                    checkEmail(email)
                }
                else{
                    // 데이터가 존재하지 않는 경우 새로 로그인
                    var joinBundle = Bundle()
                    joinBundle.putString("joinUserEmail",email)
                    joinBundle.putLong("joinUserType",1)
                    mainActivity.replaceFragment(MainActivity.JOIN_SELLER_ADD_INFO_FRAGMENT,false,joinBundle)
                }


            }
        }
    }
    fun naverLogin(){

        // 사용자 정보 가져오기
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                val email = result.profile?.email.toString()
                checkEmail(email)
                // 로그인 및 사용자 정보 처리
            }

            override fun onError(errorCode: Int, message: String) {
                // 사용자 정보 가져오기 실패 처리
            }

            override fun onFailure(httpStatus: Int, message: String) {
                // 사용자 정보 가져오기 실패 처리
            }
        })

    }
    fun checkEmail(loginSellerEmail:String){
        getUserSellerInfoById(loginSellerEmail) {
            // 가져온 데이터가 없다면
            if (it.result.exists() == false) {
                var joinBundle = Bundle()
                joinBundle.putString("joinUserEmail",loginSellerEmail)
                joinBundle.putLong("joinUserType",1)
                mainActivity.replaceFragment(MainActivity.JOIN_SELLER_ADD_INFO_FRAGMENT, false, joinBundle)
            }
            // 가져온 데이터가 있다면
            else {
                for (c1 in it.result.children) {

                    // 로그인한 사용자 정보를 가져온다.
                    val userSellerIdx = c1.child("userSellerIdx").value as Long
                    val userSellerLoginType = c1.child("userSellerLoginType").value as Long
                    val userSellerEmail = c1.child("userSellerEmail").value as String
                    val userSellerPw = c1.child("userSellerPw").value as String
                    val userSellerNickname = c1.child("userSellerNickname").value as String
                    val userSellerBanner = c1.child("userSellerBanner").value as String
                    val userSellerPostNumber = c1.child("userSellerPostNumber").value as String
                    val userSellerPostDetail = c1.child("userSellerPostDetail").value as String
                    val userSellerStoreInfo = c1.child("userSellerStoreInfo").value as String
                    val userSellerStoreName = c1.child("userSellerStoreName").value as String

                    mainActivity.loginSellerInfo = UserSellerInfo(
                        userSellerIdx,
                        userSellerLoginType,
                        userSellerEmail,
                        userSellerPw,
                        userSellerNickname,
                        userSellerBanner,
                        userSellerStoreName,
                        userSellerStoreInfo,
                        userSellerPostNumber,
                        userSellerPostDetail
                    )
                    mainActivity.isLogined = true

                    Snackbar.make(fragmentLoginSellerMainBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                    mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
                }
            }
        }
    }



}
