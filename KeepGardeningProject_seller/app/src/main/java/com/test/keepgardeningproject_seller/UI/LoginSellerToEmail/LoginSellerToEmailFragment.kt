package com.test.keepgardeningproject_seller.UI.LoginSellerToEmail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerToEmailBinding

class LoginSellerToEmailFragment : Fragment() {
    lateinit var fragmentLoginSellerToEmailBinding: FragmentLoginSellerToEmailBinding
    lateinit var mainActivity: MainActivity
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLoginSellerToEmailBinding = FragmentLoginSellerToEmailBinding.inflate(inflater)
        fragmentLoginSellerToEmailBinding.run {
            mainActivity = activity as MainActivity
            firebaseAuth = FirebaseAuth.getInstance() // firebaseAuth의 인스턴스를 가져옴

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
            textInputLayoutLoginSellerToEmailPassword.editText?.onFocusChangeListener = View.OnFocusChangeListener{_,hasFocus->
                if(!hasFocus){
                    val pwCheck = textInputLayoutLoginSellerToEmailPassword.editText?.text.toString()
                    val pwSize = pwCheck.length
                    if(pwSize<6){
                        textInputLayoutLoginSellerToEmailPassword.error = "비밀번호를 6자리 이상 입력해주세요."
                    }else{
                        textInputLayoutLoginSellerToEmailPassword.error = null
                        textInputLayoutLoginSellerToEmailPassword.isErrorEnabled = false
                    }
                }
            }

            buttonLoginSellerToEmailLogin.setOnClickListener {
                mainActivity.hideKeyboard()
                firebaseSubmit()
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
    fun firebaseSubmit() {
        fragmentLoginSellerToEmailBinding.run {
            // 사용자가 입력한 내용을 가져온다.
            val loginSellerEmail = textInputEditTextLoginSellerToEmailEmail.text.toString()
            val loginSellerPw = textInputEditTextLoginSellerToEmailPassword.text.toString()
            // 아이디 비었는지 체크
            if (loginSellerEmail.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginSellerToEmailEmail)
                }
                builder.show()
                return
            }
            // 비밀번호 비었는지 체크
            if (loginSellerPw.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginSellerToEmailPassword)
                }
                builder.show()
                return
            }
            // 파이어베이스에서 비밀번호 가져옴
            firebaseAuth.signInWithEmailAndPassword(loginSellerEmail, loginSellerPw)
                .addOnCompleteListener(requireActivity(), OnCompleteListener<AuthResult?> { task ->
                    // 로그인 성공
                    if (task.isSuccessful) {
                        UserRepository.getUserSellerInfoById(loginSellerEmail) {
                            // 가져온 데이터가 없다면
                            if (!it.result.exists()) {
                                val builder = MaterialAlertDialogBuilder(mainActivity)
                                builder.setTitle("로그인 오류")
                                builder.setMessage("존재하지 않는 아이디 입니다")
                                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                    textInputEditTextLoginSellerToEmailEmail.setText("")
                                    textInputEditTextLoginSellerToEmailPassword.setText("")
                                    mainActivity.showSoftInput(
                                        textInputEditTextLoginSellerToEmailEmail
                                    )
                                }
                                builder.show()
                            }else{
                                for (c1 in it.result.children) {
                                    val userSellerIdx = c1.child("userSellerIdx").value as Long
                                    val userSellerLoginType = c1.child("userSellerLoginType").value as Long
                                    val userSellerEmail = c1.child("userSellerEmail").value as String
                                    val userSellerPw = c1.child("userSellerPw").value as String
                                    val userSellerNickname = c1.child("userSellerNickname").value as String
                                    val userSellerBanner = c1.child("userSellerBanner").value as String
                                    val userSellerPostNumber = c1.child("userSellerPostNumber").value as String
                                    val userSellerPostDetail = c1.child("userSellerPostDetail").value as String
                                    val userSellerStoreInfo = c1.child("userSellerStoreInfo").value as String
                                    val userSellerStoreName = c1.child("userSellerStoreName").value as String

                                    mainActivity.loginSellerInfo = UserSellerInfo(
                                        userSellerIdx,
                                        userSellerLoginType,
                                        userSellerEmail,
                                        userSellerPw,
                                        userSellerNickname,
                                        userSellerBanner,
                                        userSellerStoreName,
                                        userSellerStoreInfo,
                                        userSellerPostNumber,
                                        userSellerPostDetail
                                    )
                                    mainActivity.isLogined = true

                                    Snackbar.make(fragmentLoginSellerToEmailBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()
                                    mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
                                    mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                                    mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
                                }
                            }
                        }
                    } else { // 로그인 실패
                        Snackbar.make(fragmentLoginSellerToEmailBinding.root, "로그인 오류입니다.", Snackbar.LENGTH_SHORT).show()
                    }

                })
        }
    }
}