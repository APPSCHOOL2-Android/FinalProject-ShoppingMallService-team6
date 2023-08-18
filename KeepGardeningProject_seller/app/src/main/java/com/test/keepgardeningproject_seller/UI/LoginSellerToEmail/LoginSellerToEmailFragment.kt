package com.test.keepgardeningproject_seller.UI.LoginSellerToEmail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerToEmailBinding

class LoginSellerToEmailFragment : Fragment() {
    lateinit var fragmentLoginSellerToEmailBinding : FragmentLoginSellerToEmailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginSellerToEmailBinding = FragmentLoginSellerToEmailBinding.inflate(inflater)
        fragmentLoginSellerToEmailBinding.run {
            mainActivity = activity as MainActivity

            toolbarLoginSellerToEmail.run {
                setTitle("이메일로 로그인하기")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                }
            }

            // 포커스가 올라가고 이메일 형식이 맞는지 체크
            textInputLayoutLoginSellerToEmailEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutLoginSellerToEmailEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutLoginSellerToEmailEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutLoginSellerToEmailEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutLoginSellerToEmailEmail.error = null
                        textInputLayoutLoginSellerToEmailEmail.isErrorEnabled = false
                    }
                }
            }

            buttonLoginSellerToEmailLogin.setOnClickListener {
                val emailCheck = textInputLayoutLoginSellerToEmailEmail.editText?.text.toString()
                val passwordCheck = textInputLayoutLoginSellerToEmailPassword.editText?.text.toString()
                val emailError = textInputLayoutLoginSellerToEmailEmail.error
                if (emailCheck.isNotEmpty() && passwordCheck.isNotEmpty() && emailError == null) {
                    mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
                }else{
                    textViewLoginSellerToEmailCheckLogin.visibility = View.VISIBLE
                }
            }
            textViewLoginSellerToEmailFindPwButton.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_FIND_PW_FRAGMENT, true, null)
                // 넘어갔다가 돌아올시 editText null처리하기
                textInputLayoutLoginSellerToEmailEmail.editText?.text = null
                textInputLayoutLoginSellerToEmailPassword.editText?.text = null
                textInputLayoutLoginSellerToEmailEmail.error = null
                textInputLayoutLoginSellerToEmailEmail.isErrorEnabled = false
            }
            textViewLoginSellerToEmailJoinEmailButton.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.JOIN_SELLER_MAIN_FRAGMENT, true, null)
                // 넘어갔다가 돌아올시 editText null처리하기
                textInputLayoutLoginSellerToEmailEmail.editText?.text = null
                textInputLayoutLoginSellerToEmailPassword.editText?.text = null
                textInputLayoutLoginSellerToEmailEmail.error = null
                textInputLayoutLoginSellerToEmailEmail.isErrorEnabled = false
            }
        }

        return fragmentLoginSellerToEmailBinding.root
    }

}