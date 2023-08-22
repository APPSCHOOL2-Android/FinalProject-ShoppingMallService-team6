package com.test.keepgardeningproject_customer.UI.JoinCustomerMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentJoinCustomerMainBinding

class JoinCustomerMainFragment : Fragment() {
    lateinit var fragmentJoinCustomerMainBinding: FragmentJoinCustomerMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentJoinCustomerMainBinding = FragmentJoinCustomerMainBinding.inflate(inflater)
        fragmentJoinCustomerMainBinding.run {
            toolbarJoinCustomerMain.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.JOIN_CUSTOMER_MAIN_FRAGMENT)
                }
                setTitle("회원가입 하기")
            }

            // 이메일 포커스 주기
            textInputLayoutJoinCustomerMainEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutJoinCustomerMainEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutJoinCustomerMainEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutJoinCustomerMainEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutJoinCustomerMainEmail.error = null
                        textInputLayoutJoinCustomerMainEmail.isErrorEnabled = false
                    }
                }
            }
            // 중복확인하기
            buttonJoinCustomerMainDoubleCheck.setOnClickListener {
                Toast.makeText(requireContext(),"중복확인",Toast.LENGTH_SHORT).show()
            }
            // 로그인 화면으로
            buttonJoinCustomerMainJoin.setOnClickListener {
                userSubmit()
            }

        }
        return fragmentJoinCustomerMainBinding.root
    }

    fun userSubmit(){
        fragmentJoinCustomerMainBinding.run {

            //이메일,패스워드,닉네임
            var email = textInputEditTextJoinCustomerMainEmail.text.toString()
            var pw = textInputEditTextJoinCustomerMainPassword.text.toString()
            var nickNames = textInputEditTextJoinCustomerMainNickName.text.toString()

            UserRepository.getUserIndex {
                var userindex = it.result.value as Long

                userindex++
                val userinfo = UserInfo(userindex,0,email,pw,nickNames)

                UserRepository.setUserInfo(userinfo){
                    UserRepository.setUserIdx(userindex){
                        mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT)
                    }
                }


            }



        }
    }



}