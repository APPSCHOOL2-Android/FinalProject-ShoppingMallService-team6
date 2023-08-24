package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.UserDAO
import com.test.keepgardeningproject_customer.DAO.UserInfo

import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.loginedUserInfo
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerModifyBinding
import kotlin.math.log

class MyPageCustomerModifyFragment : Fragment() {

    lateinit var myPageCustomerModifyBinding: FragmentMyPageCustomerModifyBinding
    lateinit var myPageCustomerModifyViewModel: MyPageCustomerModifyViewModel
    lateinit var mainActivity :MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageCustomerModifyBinding = FragmentMyPageCustomerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        myPageCustomerModifyViewModel = ViewModelProvider(mainActivity)[MyPageCustomerModifyViewModel::class.java]


        //viewModel
        myPageCustomerModifyViewModel.run {
            newNicknameData.observe(mainActivity){
                myPageCustomerModifyBinding.textInputEditTextMcNickName.setText(it)
            }
            newPasswordData.observe(mainActivity){
                myPageCustomerModifyBinding.textInputEditTextMcPassword.setText(it)
            }
        }

        myPageCustomerModifyBinding.run {


            var idxs = loginedUserInfo.userIdx!!
            var loginTypes = loginedUserInfo.userLoginType!!
            var emails =loginedUserInfo.userEmail!!

            //이메일
            if(loginTypes == 0L){
                toolbarMc.run {
                    setTitle("정보 수정")
                    setNavigationIcon(R.drawable.ic_back_24px)
                    setNavigationOnClickListener {
                        //마이메이지 메인화면으로 이동
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                    }
                }
                buttonMcModify.run {

                    setOnClickListener {
                        var newpw = textInputEditTextMcPassword.text.toString()
                        var newnick = textInputEditTextMcNickName.text.toString()

                        if(textInputEditTextMcPassword.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcPassword)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if(textInputEditTextMcPasswordCheck.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호 확인을 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcPasswordCheck)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if(textInputEditTextMcNickName.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("닉네임을 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcNickName)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if(textInputEditTextMcPassword.text.toString() != textInputEditTextMcPasswordCheck.text.toString()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 똑같이 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputLayoutMcPassword)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var newclass = UserInfo(idxs, loginTypes,emails,newpw,newnick)
                        UserRepository.modifyUserInfo(newclass){
                            myPageCustomerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageCustomerModifyBinding.root, "수정되었습니다", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }

            }
            //카카오
            if(loginTypes == 1L){
                textInputEditTextMcPassword.visibility = View.GONE
                textInputEditTextMcPasswordCheck.visibility = View.GONE
                toolbarMc.run {
                    setTitle("정보 수정")
                    setNavigationIcon(R.drawable.ic_back_24px)
                    setNavigationOnClickListener {
                        //마이메이지 메인화면으로 이동
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                    }
                }
                buttonMcModify.run {

                    setOnClickListener {

                        //카카오,네이버,구글로 로그인시 pw는 null 대신 none으로 설정
                        var newPw = "None"

                        var newnick = textInputEditTextMcNickName.text.toString()

                        if(textInputEditTextMcNickName.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("닉네임을 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcNickName)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var newclass = UserInfo(idxs, loginTypes,emails,newPw,newnick)

                        UserRepository.modifyUserInfo(newclass){
                            myPageCustomerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageCustomerModifyBinding.root, "수정되었습니다", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }
            //네이버
            if(loginTypes == 2L){
                textInputEditTextMcPassword.visibility = View.GONE
                textInputEditTextMcPasswordCheck.visibility = View.GONE
                toolbarMc.run {
                    setTitle("정보 수정")
                    setNavigationIcon(R.drawable.ic_back_24px)
                    setNavigationOnClickListener {
                        //마이메이지 메인화면으로 이동
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                    }
                }
                buttonMcModify.run {

                    setOnClickListener {

                        //카카오,네이버,구글로 로그인시 pw는 null 대신 none으로 설정
                        var newPw = "None"

                        var newnick = textInputEditTextMcNickName.text.toString()

                        if(textInputEditTextMcNickName.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("닉네임을 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcNickName)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var newclass = UserInfo(idxs, loginTypes,emails,newPw,newnick)

                        UserRepository.modifyUserInfo(newclass){
                            myPageCustomerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageCustomerModifyBinding.root, "수정되었습니다", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }
            //구글
            if(loginTypes == 3L){
                textInputEditTextMcPassword.visibility = View.GONE
                textInputEditTextMcPasswordCheck.visibility = View.GONE
                toolbarMc.run {
                    setTitle("정보 수정")
                    setNavigationIcon(R.drawable.ic_back_24px)
                    setNavigationOnClickListener {
                        //마이메이지 메인화면으로 이동
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                    }
                }
                buttonMcModify.run {

                    setOnClickListener {

                        //카카오,네이버,구글로 로그인시 pw는 null 대신 none으로 설정
                        var newPw = "None"

                        var newnick = textInputEditTextMcNickName.text.toString()

                        if(textInputEditTextMcNickName.text!!.isEmpty()){
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("닉네임을 입력해주세요")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMcNickName)
                            }
                            builder.setNegativeButton("취소",null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var newclass = UserInfo(idxs, loginTypes,emails,newPw,newnick)

                        UserRepository.modifyUserInfo(newclass){
                            myPageCustomerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageCustomerModifyBinding.root, "수정되었습니다", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }


        }

        return  myPageCustomerModifyBinding.root
    }



}