package com.test.keepgardeningproject_seller.UI.HomeSellerMyPageMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.test.keepgardeningproject_seller.API.GoogleAPI
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.API.NaverAPI
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerMyPageMainBinding

class HomeSellerMyPageMainFragment : Fragment() {

    lateinit var homeSellerMyPageMainBinding: FragmentHomeSellerMyPageMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
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
    fun withDrawDiaglog(){
        val dialog = MaterialDialog(requireContext())
            .title(text = "회원탈퇴 ")
            .message(text = "정말 탈퇴하실건가요?")
            .positiveButton(text = "네") { dialog ->
                // OK 버튼을 눌렀을 때 동작
                // 여기에 원하는 로직을 추가
                UserRepository.deleteUserSellerInfo(mainActivity.loginSellerInfo.userSellerIdx){task ->
                    if (task.isSuccessful) {
                        mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT,false,null)
                    } else {
                        // 삭제 실패한 경우 처리
                    }
                }
                dialog.dismiss() // 다이얼로그 닫기
            }
            .negativeButton(text = "아니요") { dialog ->
                // Cancel 버튼을 눌렀을 때 동작
                // 여기에 원하는 로직을 추가
                dialog.dismiss() // 다이얼로그 닫기
            }

// 다이얼로그 표시
        dialog.show()
    }


}