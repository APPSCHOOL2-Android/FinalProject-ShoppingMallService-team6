package com.test.keepgardeningproject_customer.UI.JoinCustomerMain

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kakao.sdk.user.model.User
import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.loginedUserInfo
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentJoinCustomerMainBinding

class JoinCustomerMainFragment : Fragment() {
    lateinit var fragmentJoinCustomerMainBinding: FragmentJoinCustomerMainBinding
    lateinit var mainActivity: MainActivity
    private var firebaseAuth: FirebaseAuth? = null
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentJoinCustomerMainBinding = FragmentJoinCustomerMainBinding.inflate(inflater)

        fragmentJoinCustomerMainBinding.run {
            // firebaseAuth의 인스턴스를 가져옴
            firebaseAuth = FirebaseAuth.getInstance()
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

            }



            // 로그인 화면으로
            buttonJoinCustomerMainJoin.setOnClickListener {
                userSubmit()

            }
        }
        return fragmentJoinCustomerMainBinding.root
    }

    fun userSubmit() {
        fragmentJoinCustomerMainBinding.run {

            //이메일,패스워드,닉네임,배너,상점명, 상점설명, 우편번호, 상세주소
            var email = textInputEditTextJoinCustomerMainEmail.text.toString()
            var pw = textInputEditTextJoinCustomerMainPassword.text.toString()
            var nickNames = textInputEditTextJoinCustomerMainNickName.text.toString()
            val user: FirebaseUser? = firebaseAuth!!.currentUser
            UserRepository.getUserIndex {
                var userindex = it.result.value as Long

                userindex++


                firebaseAuth!!.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if(isAdded){
                            if (task.isSuccessful) {
                                Toast.makeText(requireContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                                mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT,false,null)

                            } else {
                                Toast.makeText(requireContext(),"이미 존재하는 계정입니다.",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                val userinfo = user?.email?.let { it1 ->
                    UserInfo(userindex,MainActivity.EMAIL_LOGIN,email,"None",nickNames)
                }
                if (userinfo != null) {
                    loginedUserInfo = userinfo
                    UserRepository.setUserInfo(loginedUserInfo){
                        UserRepository.setUserIdx(userindex){
                            Snackbar.make(fragmentJoinCustomerMainBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                            mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT)
                            mainActivity.removeFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT)
                        }
                    }
                }
            }
        }
    }



}