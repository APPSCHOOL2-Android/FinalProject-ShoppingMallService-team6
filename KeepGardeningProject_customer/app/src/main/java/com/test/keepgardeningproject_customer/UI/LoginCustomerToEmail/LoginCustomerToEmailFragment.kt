package com.test.keepgardeningproject_customer.UI.LoginCustomerToEmail

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentLoginCustomerToEmailBinding

class LoginCustomerToEmailFragment : Fragment() {
    lateinit var fragmentLoginCustomerToEmailBinding : FragmentLoginCustomerToEmailBinding
    lateinit var mainActivity: MainActivity
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginCustomerToEmailBinding = FragmentLoginCustomerToEmailBinding.inflate(inflater)
        fragmentLoginCustomerToEmailBinding.run {
            mainActivity = activity as MainActivity
            firebaseAuth = FirebaseAuth.getInstance()
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
                mainActivity.hideKeyboard()
                firebaseSubmit()
            }
            textInputLayoutLoginCustomerToEmailPassword.editText?.onFocusChangeListener = View.OnFocusChangeListener{_,hasFocus->
                if(!hasFocus){
                    val pwCheck = textInputLayoutLoginCustomerToEmailPassword.editText?.text.toString()
                    val pwSize = pwCheck.length
                    if(pwSize<6){
                        textInputLayoutLoginCustomerToEmailPassword.error = "비밀번호를 6자리 이상 입력해주세요."
                    }else{
                        textInputLayoutLoginCustomerToEmailPassword.error = null
                        textInputLayoutLoginCustomerToEmailPassword.isErrorEnabled = false
                    }
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
    fun firebaseSubmit() {
        fragmentLoginCustomerToEmailBinding.run {
            // 사용자가 입력한 내용을 가져온다.
            val loginCustomerEmail = textInputEditTextLoginCustomerToEmailEmail.text.toString()
            val loginCustomerPw = textInputEditTextLoginCustomerToEmailPassword.text.toString()
            // 아이디 비었는지 체크
            if (loginCustomerEmail.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginCustomerToEmailEmail)
                }
                builder.show()
                return
            }
            // 비밀번호 비었는지 체크
            if (loginCustomerPw.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginCustomerToEmailPassword)
                }
                builder.show()
                return
            }
            // 파이어베이스에서 비밀번호 가져옴
            firebaseAuth.signInWithEmailAndPassword(loginCustomerEmail, loginCustomerPw)
                .addOnCompleteListener(requireActivity(), OnCompleteListener<AuthResult?> { task ->
                    // 로그인 성공
                    if (task.isSuccessful) {
                        //Toast.makeText(requireActivity(), "로그인 성공", Toast.LENGTH_SHORT).show()
                        UserRepository.getUserInfoById(loginCustomerEmail) {
                            // 가져온 데이터가 없다면
                            if (!it.result.exists()) {
                                val builder = MaterialAlertDialogBuilder(mainActivity)
                                builder.setTitle("로그인 오류")
                                builder.setMessage("존재하지 않는 아이디 입니다")
                                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                    textInputEditTextLoginCustomerToEmailEmail.setText("")
                                    textInputEditTextLoginCustomerToEmailPassword.setText("")
                                    mainActivity.showSoftInput(
                                        textInputEditTextLoginCustomerToEmailEmail
                                    )
                                }
                                builder.show()
                            }else{
                                for (c1 in it.result.children) {
                                    val userCustomerIdx = c1.child("userIdx").value as Long
                                    val userCustomerLoginType = c1.child("userLoginType").value as Long
                                    val userCustomerEmail = c1.child("userEmail").value as String
                                    val userCustomerPw = c1.child("userPw").value as String
                                    val userCustomerNickname = c1.child("userNickname").value as String
                                    MainActivity.loginedUserInfo = UserInfo(userCustomerIdx,userCustomerLoginType,userCustomerEmail,userCustomerPw,userCustomerNickname)
                                    MainActivity.isLogined = true
                                    Snackbar.make(fragmentLoginCustomerToEmailBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()
                                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT)
                                    mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT)
                                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_MAIN_FRAGMENT, false, null)
                                }
                            }
                        }
                    } else { // 로그인 실패
                        //Toast.makeText(requireActivity(), "로그인 오류", Toast.LENGTH_SHORT).show()
                        Snackbar.make(fragmentLoginCustomerToEmailBinding.root, "로그인 오류입니다.", Snackbar.LENGTH_SHORT).show()
                    }

                })
        }
    }

}