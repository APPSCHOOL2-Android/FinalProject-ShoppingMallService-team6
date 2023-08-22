package com.test.keepgardeningproject_customer.UI.LoginCustomerToEmail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
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
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_MAIN_FRAGMENT, false, null)
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