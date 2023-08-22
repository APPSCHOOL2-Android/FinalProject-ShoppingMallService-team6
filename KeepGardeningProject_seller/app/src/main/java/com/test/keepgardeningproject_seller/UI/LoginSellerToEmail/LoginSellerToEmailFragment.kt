package com.test.keepgardeningproject_seller.UI.LoginSellerToEmail

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentLoginSellerToEmailBinding

class LoginSellerToEmailFragment : Fragment() {
    lateinit var fragmentLoginSellerToEmailBinding: FragmentLoginSellerToEmailBinding
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
//                val emailCheck = textInputLayoutLoginSellerToEmailEmail.editText?.text.toString()
//                val passwordCheck = textInputLayoutLoginSellerToEmailPassword.editText?.text.toString()
//                val emailError = textInputLayoutLoginSellerToEmailEmail.error
//                if (emailCheck.isNotEmpty() && passwordCheck.isNotEmpty() && emailError == null) {
//                    mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
//                }else{
//                    textViewLoginSellerToEmailCheckLogin.visibility = View.VISIBLE
//                }
                //mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
                loginSubmit()
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

    fun loginSubmit() {
        fragmentLoginSellerToEmailBinding.run {
            // 사용자가 입력한 내용을 가져온다.
            val loginSellerEmail = textInputEditTextLoginSellerToEmailEmail.text.toString()
            val loginSellerPw = textInputEditTextLoginSellerToEmailPassword.text.toString()

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

            UserRepository.getUserSellerInfoById(loginSellerEmail) {
                // 가져온 데이터가 없다면
                if (it.result.exists() == false) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("로그인 오류")
                    builder.setMessage("존재하지 않는 아이디 입니다")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        textInputEditTextLoginSellerToEmailEmail.setText("")
                        textInputEditTextLoginSellerToEmailPassword.setText("")
                        mainActivity.showSoftInput(textInputEditTextLoginSellerToEmailEmail)
                    }
                    builder.show()
                }
                // 가져온 데이터가 있다면
                else {
                    for (c1 in it.result.children) {
                        // 가져온 데이터에서 비밀번호를 가져온다.
                        val userSellerPw = c1.child("userSellerPw").value as String

                        // 입력한 비밀번호와 현재 계정의 비밀번호가 다르다면
                        if (loginSellerPw != userSellerPw) {
                            val builder = MaterialAlertDialogBuilder(mainActivity)
                            builder.setTitle("로그인 오류")
                            builder.setMessage("잘못된 비밀번호 입니다")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                textInputEditTextLoginSellerToEmailPassword.setText("")
                                mainActivity.showSoftInput(textInputEditTextLoginSellerToEmailPassword)
                            }
                            builder.show()
                        }
                        // 입력한 비밀번호와 현재 계정의 비밀번호가 같다면
                        else {
                            // 로그인한 사용자 정보를 가져온다.
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
                            Snackbar.make(fragmentLoginSellerToEmailBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                            mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT, false, null)
                            Log.i("Seungheon", mainActivity.loginSellerInfo.toString())
                        }
                    }
                }
            }
        }
    }
}