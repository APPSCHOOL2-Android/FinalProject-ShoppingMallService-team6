package com.test.keepgardeningproject_seller.UI.LoginSellerFindPw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerFindPwBinding

class LoginSellerFindPwFragment : Fragment() {

    lateinit var fragmentLoginSellerFindPwBinding: FragmentLoginSellerFindPwBinding
    lateinit var mainActivity: MainActivity
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentLoginSellerFindPwBinding = FragmentLoginSellerFindPwBinding.inflate(inflater)
        fragmentLoginSellerFindPwBinding.run {
            firebaseAuth = FirebaseAuth.getInstance()
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
                FindPw(textInputEditTextLoginSellerFindPwEmail.text.toString())
                Toast.makeText(requireContext(),"이메일 인증하기 버튼 클릭", Toast.LENGTH_SHORT).show()
            }
        }


        return fragmentLoginSellerFindPwBinding.root
    }
    fun FindPw(email:String){
        firebaseAuth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Snackbar.make(fragmentLoginSellerFindPwBinding.root, "재설정 이메일 발송\n이메일을 확인해주세요", Snackbar.LENGTH_SHORT).show()
                mainActivity.removeFragment(MainActivity.LOGIN_SELLER_FIND_PW_FRAGMENT)
            } else {
                Snackbar.make(fragmentLoginSellerFindPwBinding.root, "계정을 확인할 수 없습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }



}