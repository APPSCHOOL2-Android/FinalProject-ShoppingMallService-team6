package com.test.keepgardeningproject_customer.UI.LoginCustomerMain

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.test.keepgardeningproject_customer.API.KakaoAPI
import com.test.keepgardeningproject_customer.API.NaverAPI
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentLoginCustomerMainBinding

class LoginCustomerMainFragment : Fragment() {

    lateinit var fragmentLoginCustomerMainBinding:FragmentLoginCustomerMainBinding
    lateinit var mainActivity: MainActivity

    // 카카오톡 API 불러오기
    private val kakaoApi = KakaoAPI()
    // 네이버 API 불러오기
    private val naverApi = NaverAPI()

    lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        NaverIdLoginSDK.initialize(requireContext(), "el7gHNDTrFweYrwQdjFT", "Kp1DmQI6qv", "킵 가드닝")
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
                kakaoApi.checkKaKaoLogin(requireContext())
                kakaoLogin()
            }
            // 네이버 로그인
            buttonLoginCustomerMainNaverLogin.setOnClickListener {
                naverApi.checkNaverLogin(requireContext())
                naverLogin()
            }
            // 구글 로그인
            buttonLoginCustomerMainGoogleLogin.setOnClickListener {
                googleLogin()
                signInWithGoogle()
                val account = GoogleSignIn.getLastSignedInAccount(requireContext())
                val userEmail = account?.email
                if (userEmail != null) {
                    checkEmail(userEmail,MainActivity.GOOGLE_LOGIN)
                }
            }
            // 이메일 로그인
            buttonLoginCustomerMainEmailLogin.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT,true,null)
            }
        }

        return fragmentLoginCustomerMainBinding.root
    }
    fun kakaoLogin(){
        // 로그인 정보 받아오기

        UserApiClient.instance.me { user, error ->
            if (error != null) {
            } else if (user != null) {
                var email = user.kakaoAccount?.email
                if (email != null) {
                    checkEmail(email,MainActivity.KAKAO_LOGIN)
                }
                else{
                    // 데이터가 존재하지 않는 경우 새로 로그인
                    var joinBundle = Bundle()
                    joinBundle.putString("joinUserEmail",email)
                    joinBundle.putLong("joinUserType",MainActivity.KAKAO_LOGIN)
                    mainActivity.replaceFragment(MainActivity.JOIN_CUSTOMER_ADD_INFO_FRAGMENT,false,joinBundle)
                }


            }
        }
    }
    fun naverLogin(){

        // 사용자 정보 가져오기
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                val email = result.profile?.email.toString()
                checkEmail(email,MainActivity.NAVER_LOGIN)
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

    fun googleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }
    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val userEmail = account?.email
            // userEmail을 이용하여 이메일 처리
        } catch (e: ApiException) {
            // 로그인 실패 처리
        }
    }
    fun checkEmail(loginCustomerEmail:String, joinUserType:Long){
        UserRepository.getUserInfoById(loginCustomerEmail){
            // 가져온 데이터가 없다면?
            if(!it.result.exists()){
                val joinBundle = Bundle()
                joinBundle.putString("joinUserEmail",loginCustomerEmail)
                joinBundle.putLong("joinUserType",joinUserType)
                mainActivity.replaceFragment(MainActivity.JOIN_CUSTOMER_ADD_INFO_FRAGMENT,false,joinBundle)
            }else{
                for(c1 in it.result.children){
                    // 로그인한 사용자 정보를 가져온다.
                    val userCustomerIdx = c1.child("userIdx").value as Long
                    var userCustomerEmail = c1.child("userEmail").value as String
                    var userCustomerLoginType = c1.child("userLoginType").value as Long
                    var userCustomerNickName = c1.child("userNickname").value as String
                    var userCustomerPw = c1.child("userPw").value as String
                    MainActivity.loginedUserInfo = UserInfo(
                        userCustomerIdx,
                        userCustomerLoginType,
                        userCustomerEmail,
                        userCustomerPw,
                        userCustomerNickName
                    )
                    MainActivity.isLogined = true
                    Snackbar.make(fragmentLoginCustomerMainBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_MAIN_FRAGMENT, false, null)
                }
            }
        }
    }

}