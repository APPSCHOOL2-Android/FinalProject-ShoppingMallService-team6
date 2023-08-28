package com.test.keepgardeningproject_seller.UI.LoginSellerMain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.API.NaverAPI
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.UserRepository.Companion.getUserSellerInfoById
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerMainBinding

class LoginSellerMainFragment : Fragment() {

    private lateinit var fragmentLoginSellerMainBinding: FragmentLoginSellerMainBinding
    lateinit var mainActivity: MainActivity

    // 카카오톡 API 불러오기
    private val kakaoApi = KakaoAPI()
    // 네이버 API 불러오기
    private val naverApi = NaverAPI()

    // val sharedPref = requireContext().getSharedPreferences("myLogin", Context.MODE_PRIVATE)

    lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }
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
                naverApi.checkNaverLogin(requireContext())
                naverLogin()
            }
            // 구글 로그인
            buttonLoginSellerMainGoogleLogin.setOnClickListener {
                googleLogin()
                signInWithGoogle()
                val account = GoogleSignIn.getLastSignedInAccount(requireContext())
                val userEmail = account?.email
                if (userEmail != null) {
                    checkEmail(userEmail,MainActivity.GOOGLE_LOGIN)
                }

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
                    checkEmail(email,MainActivity.KAKAO_LOGIN)
                }
                else{
                    // 데이터가 존재하지 않는 경우 새로 로그인
                    var joinBundle = Bundle()
                    joinBundle.putString("joinUserEmail",email)
                    joinBundle.putLong("joinUserType",MainActivity.KAKAO_LOGIN)
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
            Log.e("email","$userEmail")
            // userEmail을 이용하여 이메일 처리
        } catch (e: ApiException) {
            // 로그인 실패 처리
        }
    }

    fun checkEmail(loginSellerEmail:String, joinUserType:Long){
        getUserSellerInfoById(loginSellerEmail) {
            // 가져온 데이터가 없다면
            if (!it.result.exists()) {
                val joinBundle = Bundle()
                joinBundle.putString("joinUserEmail",loginSellerEmail)
                joinBundle.putLong("joinUserType",joinUserType)

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
