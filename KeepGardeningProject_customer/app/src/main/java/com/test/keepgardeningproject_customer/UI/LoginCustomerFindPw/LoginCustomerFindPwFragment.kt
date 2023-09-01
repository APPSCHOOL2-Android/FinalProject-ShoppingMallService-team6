package com.test.keepgardeningproject_customer.UI.LoginCustomerFindPw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentLoginCustomerFindPwBinding

class LoginCustomerFindPwFragment : Fragment() {

    lateinit var fragmentLoginCustomerFindPwBinding: FragmentLoginCustomerFindPwBinding
    lateinit var mainActivity: MainActivity
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentLoginCustomerFindPwBinding = FragmentLoginCustomerFindPwBinding.inflate(inflater)
        fragmentLoginCustomerFindPwBinding.run {
            firebaseAuth = FirebaseAuth.getInstance()
            toolbarLoginCustomerFindPw.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener{
                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_FIND_PW_FRAGMENT)
                }
                setTitle("비밀번호 찾기")
            }
            // 포커스가 올라가고 이메일 형식이 맞는지 체크
            textInputLayoutLoginCustomerFindPwEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkEmailRight()
                }
            }
            buttonLoginCustomerFindPwCertification.setOnClickListener {
                if(checkEmailRight()){
                    FindPw(textInputEditTextLoginCustomerFindPwEmail.text.toString())
                }

            }
        }


        return fragmentLoginCustomerFindPwBinding.root
    }
    fun FindPw(email:String){
        firebaseAuth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mainActivity.hideKeyboard()
                Snackbar.make(fragmentLoginCustomerFindPwBinding.root, "재설정 이메일 발송\n이메일을 확인해주세요", Snackbar.LENGTH_SHORT).show()
                mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_FIND_PW_FRAGMENT)
            } else {
                mainActivity.hideKeyboard()
                Snackbar.make(fragmentLoginCustomerFindPwBinding.root, "계정을 확인할 수 없습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    fun checkEmailRight():Boolean{
        fragmentLoginCustomerFindPwBinding.run {
            var emailCheck = textInputLayoutLoginCustomerFindPwEmail.editText?.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (emailCheck.isEmpty()) {
                textInputLayoutLoginCustomerFindPwEmail.error = "이메일을 입력해주세요"
                return false
            } else if (!emailCheck.matches(emailPattern.toRegex())) {
                textInputLayoutLoginCustomerFindPwEmail.error = "이메일 형식이 잘못되었습니다."
                return true
            } else {
                textInputLayoutLoginCustomerFindPwEmail.error = null
                textInputLayoutLoginCustomerFindPwEmail.isErrorEnabled = false
                return true
            }
        }

    }


}