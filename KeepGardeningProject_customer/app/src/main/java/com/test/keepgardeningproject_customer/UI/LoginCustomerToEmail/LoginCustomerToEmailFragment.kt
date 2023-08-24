package com.test.keepgardeningproject_customer.UI.LoginCustomerToEmail

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentLoginCustomerToEmailBinding

class LoginCustomerToEmailFragment : Fragment() {
    lateinit var fragmentLoginCustomerToEmailBinding : FragmentLoginCustomerToEmailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginCustomerToEmailBinding = FragmentLoginCustomerToEmailBinding.inflate(inflater)
        fragmentLoginCustomerToEmailBinding.run {
            mainActivity = activity as MainActivity

            toolbarLoginCustomerToEmail.run {
                setTitle("이메일로 로그인하기")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT)
                }
            }

            // 포커스가 올라가고 이메일 형식이 맞는지 체크
            textInputLayoutLoginCustomerToEmailEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutLoginCustomerToEmailEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutLoginCustomerToEmailEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutLoginCustomerToEmailEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutLoginCustomerToEmailEmail.error = null
                        textInputLayoutLoginCustomerToEmailEmail.isErrorEnabled = false
                    }
                }
            }

            buttonLoginCustomerToEmailLogin.setOnClickListener {
                val emailCheck = textInputLayoutLoginCustomerToEmailEmail.editText?.text.toString()
                val passwordCheck = textInputLayoutLoginCustomerToEmailPassword.editText?.text.toString()
                val emailError = textInputLayoutLoginCustomerToEmailEmail.error
                if (emailCheck.isNotEmpty() && passwordCheck.isNotEmpty() && emailError == null) {
                    // 로그인 완료시 구현
                    // 아이디, 비밀번호가 있는지 검증
                    // 검증완료시
                    // 1. MainActivity
                    //      isLogined = true
                    //      loginedUserInfo 객체 저장
                    // 2.홈화면의 마이페이지화면으로 이동하기
                    UserRepository.getUserInfoById(emailCheck){
                        // 회원가입된 이메일이 없다면
                        if(it.result.exists() == false) {
                            val builder = MaterialAlertDialogBuilder(mainActivity)
                            builder.setTitle("로그인 오류")
                            builder.setMessage("존재하지 않는 아이디 입니다")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputLayoutLoginCustomerToEmailEmail)
                            }
                            builder.show()
                        }
                        // 회원가입된 이메일이 있다면
                        else{
                            for(c1 in it.result.children){
                                val userPw = c1.child("userPw").value as String

                                // 비밀번호 검증
                                if(passwordCheck != userPw){
                                    if(it.result.exists() == false) {
                                        val builder = MaterialAlertDialogBuilder(mainActivity)
                                        builder.setTitle("로그인 오류")
                                        builder.setMessage("잘못된 비밀번호 입니다")
                                        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                            mainActivity.showSoftInput(
                                                textInputLayoutLoginCustomerToEmailEmail
                                            )
                                        }
                                        builder.show()
                                    }
                                }
                                else{
//                                    var userIdx: Long?,
//                                    var userLoginType:Long?,
//                                    var userEmail:String?,
//                                    val userPw:String?,
//                                    var userNickname: String?)
                                    val userIdx = c1.child("userIdx").value as Long
                                    val userLoginType = c1.child("userLoginType").value as Long
                                    val userEmail = c1.child("userEmail").value as String
                                    val userPw = c1.child("userPw").value as String
                                    val userNickname = c1.child("userNickname").value as String

                                     val loginedUserInfo = UserInfo(
                                        userIdx = userIdx,
                                        userLoginType = userLoginType,
                                        userEmail = userEmail,
                                        userPw = userPw,
                                        userNickname = userNickname
                                    )

                                    MainActivity.loginedUserInfo = loginedUserInfo
                                    MainActivity.isLogined = true

                                    Snackbar.make(fragmentLoginCustomerToEmailBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT)
                                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT)
                                }

                            }
                        }
                    }

                }else{
                    textViewLoginCustomerToEmailCheckLogin.visibility = View.VISIBLE
                }
            }
            textViewLoginCustomerToEmailFindPwButton.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_FIND_PW_FRAGMENT, true, null)
                // 넘어갔다가 돌아올시 editText null처리하기
                textInputLayoutLoginCustomerToEmailEmail.editText?.text = null
                textInputLayoutLoginCustomerToEmailPassword.editText?.text = null
                textInputLayoutLoginCustomerToEmailEmail.error = null
                textInputLayoutLoginCustomerToEmailEmail.isErrorEnabled = false
            }
            textViewLoginCustomerToEmailJoinEmailButton.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.JOIN_CUSTOMER_MAIN_FRAGMENT, true, null)
                // 넘어갔다가 돌아올시 editText null처리하기
                textInputLayoutLoginCustomerToEmailEmail.editText?.text = null
                textInputLayoutLoginCustomerToEmailPassword.editText?.text = null
                textInputLayoutLoginCustomerToEmailEmail.error = null
                textInputLayoutLoginCustomerToEmailEmail.isErrorEnabled = false
            }
        }

        return fragmentLoginCustomerToEmailBinding.root
    }

}