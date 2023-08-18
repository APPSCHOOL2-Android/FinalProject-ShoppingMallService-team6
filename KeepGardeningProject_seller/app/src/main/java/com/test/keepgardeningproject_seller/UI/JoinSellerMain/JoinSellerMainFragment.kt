package com.test.keepgardeningproject_seller.UI.JoinSellerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentJoinSellerMainBinding

class JoinSellerMainFragment : Fragment() {
    lateinit var fragmentJoinSellerMainBinding: FragmentJoinSellerMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentJoinSellerMainBinding = FragmentJoinSellerMainBinding.inflate(inflater)
        fragmentJoinSellerMainBinding.run {
            toolbarJoinSellerMain.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.JOIN_SELLER_MAIN_FRAGMENT)
                }
                setTitle("회원가입 하기")
            }


            // 이메일 포커스 주기
            textInputLayoutJoinSellerMainEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutJoinSellerMainEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutJoinSellerMainEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutJoinSellerMainEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutJoinSellerMainEmail.error = null
                        textInputLayoutJoinSellerMainEmail.isErrorEnabled = false
                    }
                }
            }
            // 중복확인하기
            buttonJoinSellerMainDoubleCheck.setOnClickListener {
                Toast.makeText(requireContext(),"중복확인", Toast.LENGTH_SHORT).show()
            }
            // 로그인 화면으로
            buttonJoinSellerMainJoin.setOnClickListener {
                mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                mainActivity.replaceFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT,false,null)
            }
        }
        return fragmentJoinSellerMainBinding.root
    }



}