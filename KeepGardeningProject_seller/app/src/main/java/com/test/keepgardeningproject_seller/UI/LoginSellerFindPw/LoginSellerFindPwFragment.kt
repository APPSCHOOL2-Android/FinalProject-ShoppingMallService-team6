package com.test.keepgardeningproject_seller.UI.LoginSellerFindPw

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerFindPwBinding

class LoginSellerFindPwFragment : Fragment() {

    lateinit var fragmentLoginSellerFindPwBinding: FragmentLoginSellerFindPwBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentLoginSellerFindPwBinding = FragmentLoginSellerFindPwBinding.inflate(inflater)
        fragmentLoginSellerFindPwBinding.run {
            toolbarLoginSellerFindPw.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener{
                    mainActivity.removeFragment(MainActivity.LOGIN_SELLER_FIND_PW_FRAGMENT)
                }
                setTitle("비밀번호 찾기")
            }
            // 포커스가 올라가고 이메일 형식이 맞는지 체크
            textInputLayoutLoginSellerFindPwEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutLoginSellerFindPwEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutLoginSellerFindPwEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutLoginSellerFindPwEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutLoginSellerFindPwEmail.error = null
                        textInputLayoutLoginSellerFindPwEmail.isErrorEnabled = false
                    }
                }
            }
            buttonLoginSellerFindPwCertification.setOnClickListener {
                Toast.makeText(requireContext(),"이메일 인증하기 버튼 클릭", Toast.LENGTH_SHORT).show()
            }
        }


        return fragmentLoginSellerFindPwBinding.root
    }



}