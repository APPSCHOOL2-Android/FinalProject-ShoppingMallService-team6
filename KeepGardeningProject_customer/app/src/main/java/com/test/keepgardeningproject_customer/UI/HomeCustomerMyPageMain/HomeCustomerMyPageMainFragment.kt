package com.test.keepgardeningproject_customer.UI.HomeCustomerMyPageMain

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.UI.HomeCustomerMain.HomeCustomerMainFragment
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMyPageMainBinding

class HomeCustomerMyPageMainFragment : Fragment() {
    lateinit var fragmentHomeCustomerMyPageMainBinding: FragmentHomeCustomerMyPageMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var homeCustomerMainFragment: HomeCustomerMainFragment
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeCustomerMyPageMainBinding = FragmentHomeCustomerMyPageMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        homeCustomerMainFragment = HomeCustomerMainFragment()
        homeCustomerMainFragment.mainActivity = activity as MainActivity
        firebaseAuth = FirebaseAuth.getInstance()
        fragmentHomeCustomerMyPageMainBinding.run {


            toolbarHcpm.run {
                setTitle("마이페이지")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_MY_PAGE_MAIN)
                }
            }
            textviewHomeCustomerMyPageMainName.run {

                var newemail = MainActivity.loginedUserInfo.userEmail.toString()
                UserRepository.getUserInfoById(newemail){
                    for(a1 in it.result.children){
                        var mynick = a1.child("userNickname").value
                        textviewHomeCustomerMyPageMainName.text = mynick.toString() + "님"
                    }
                }
                setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT,true,null)
                }
            }

            textviewHomeCustomerMyPageMainAuction.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_AUCTION_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainPurchase.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainQna.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_FRAGMENT,true,null)
            }
            textviewHomeCustomerMyPageMainReview.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_FRAGMENT,true,null)
            }
            if(MainActivity.loginedUserInfo.userLoginType == MainActivity.EMAIL_LOGIN){
                buttonHomeCustomerMyPageMainFindPw.visibility = View.VISIBLE
                buttonHomeCustomerMyPageMainFindPw.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_FIND_PW_FRAGMENT,true,null)
                }
            }

            // 로그아웃 버튼
            buttonHomeCustomerMyPageMainLogOut.setOnClickListener {
                MainActivity.isLogined = false
                MainActivity.loginedUserInfo = UserInfo(null,null,null,null,null)
                mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_MY_PAGE_MAIN)
            }
            // 회원탈퇴 버튼
            buttonHomeCustomerMyPageMainWithdrawal.setOnClickListener {
                withDrawDiaglog()

            }
        }
        return fragmentHomeCustomerMyPageMainBinding.root
    }


    fun withDrawDiaglog() {
        val builder = MaterialAlertDialogBuilder(mainActivity)
        builder.setMessage("정말 탈퇴하실건가요?")
        builder.setPositiveButton("취소", null)
        builder.setNegativeButton("확인") { dialogInterface: DialogInterface, i: Int ->
            MainActivity.loginedUserInfo.userIdx?.let {
                UserRepository.deleteUserCustoemrInfo(it) { task ->
                    if (task.isSuccessful) {
                        MainActivity.isLogined = false
                        MainActivity.loginedUserInfo = UserInfo(null,null,null,null,null)

                        if(MainActivity.loginedUserInfo.userLoginType == MainActivity.EMAIL_LOGIN){
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
                    mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT, false, null)
                }
            }
        }
        builder.show()
    }

}