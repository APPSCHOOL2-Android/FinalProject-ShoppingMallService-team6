package com.test.keepgardeningproject_seller.UI.HomeSellerMyPageMain

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_seller.API.GoogleAPI
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.API.NaverAPI
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerMyPageMainBinding

class HomeSellerMyPageMainFragment : Fragment() {

    lateinit var homeSellerMyPageMainBinding: FragmentHomeSellerMyPageMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        homeSellerMyPageMainBinding = FragmentHomeSellerMyPageMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        var kakaoAPI = KakaoAPI()
        var naverAPI = NaverAPI()
        var googleAPI = GoogleAPI()
        homeSellerMyPageMainBinding.run {
            textviewHomeSellerMyPageMainName.run {
                var newEmail = mainActivity.loginSellerInfo.userSellerEmail
                var newStorename = mainActivity.loginSellerInfo.userSellerStoreName
                UserRepository.getUserSellerInfoById(newEmail){
                    for(c1 in it.result.children){
                        var mynick = c1.child("userSellerNickname").value.toString()
                        textviewHomeSellerMyPageMainName.text = newStorename + " 상점의 " + mynick + " 님"
                    }
                }
            }


            textviewHomeSellerMyPageMainAuction.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_AUCTION_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainName.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainQna.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_QNA_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainReview.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_REVIEW_FRAGMENT,true,null)
            }
            textviewHomeSellerMyPageMainSellDelivery.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT,true,null)
            }
            when(mainActivity.loginSellerInfo.userSellerLoginType){
                MainActivity.EMAIL_LOGIN -> {
                    buttonHomeSellerMyPageMainModifyPw.visibility = View.VISIBLE
                    buttonHomeSellerMyPageMainModifyPw.setOnClickListener {
                        mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_FIND_PW_FRAGMENT,true,null)
                    }
                }
                else -> {
                    View.GONE
                }
            }

            buttonHomeSellerMyPageMainLogOut.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT,false,null)
            }

            buttonHomeSellerMyPageMainWithdrawal.setOnClickListener {
                var loginType = mainActivity.loginSellerInfo.userSellerLoginType
                when(loginType){
                    MainActivity.KAKAO_LOGIN -> {
                        kakaoAPI.kakaoWithdraw()
                        withDrawDiaglog()
                    }
                    MainActivity.NAVER_LOGIN -> {
                        naverAPI.naverWithdraw()
                        withDrawDiaglog()
                    }
                    MainActivity.GOOGLE_LOGIN -> {
                        googleAPI.googleLogOut(requireContext())
                        withDrawDiaglog()
                    }

                    MainActivity.EMAIL_LOGIN -> {
                        withDrawDiaglog()
                    }


                }
            }
        }
        return homeSellerMyPageMainBinding.root
    }
    fun withDrawDiaglog() {

        val builder = MaterialAlertDialogBuilder(mainActivity)
        builder.setMessage("정말 탈퇴하실건가요?")
        builder.setPositiveButton("취소", null)
        builder.setNegativeButton("확인") { dialogInterface: DialogInterface, i: Int ->
            UserRepository.deleteUserSellerInfo(mainActivity.loginSellerInfo.userSellerIdx) { task ->
                if (task.isSuccessful) {
                    if(mainActivity.loginSellerInfo.userSellerLoginType == MainActivity.EMAIL_LOGIN){
                        val currentUser = firebaseAuth.currentUser!!
                        currentUser.delete().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("구분", "User account deleted.")
                            }
                        }
                    }

                } else {
                    // 삭제 실패한 경우 처리
                }
                mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT, false, null)
            }
        }
        builder.show()
    }


}