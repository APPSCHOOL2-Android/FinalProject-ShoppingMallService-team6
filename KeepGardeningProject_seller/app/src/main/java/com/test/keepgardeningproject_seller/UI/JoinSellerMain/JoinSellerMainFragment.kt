package com.test.keepgardeningproject_seller.UI.JoinSellerMain

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentJoinSellerMainBinding

class JoinSellerMainFragment : Fragment() {
    lateinit var fragmentJoinSellerMainBinding: FragmentJoinSellerMainBinding
    lateinit var mainActivity: MainActivity

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null
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
                userSubmit()

            }
        }
        return fragmentJoinSellerMainBinding.root
    }

    fun userSubmit(){
        fragmentJoinSellerMainBinding.run {

            //이메일,패스워드,닉네임,배너,상점명, 상점설명, 우편번호, 상세주소
            var email = textInputEditTextJoinSellerMainEmail.text.toString()
            var pw =textInputEditTextJoinSellerMainPassword.text.toString()
            var nickNames =textInputEditTextJoinSellerMainNickName.text.toString()
            var storeName = textInputEditTextJoinSellerMainStoreName.text.toString()
            var storeInfo = textInputEditTextJoinSellerMainStoreDetail.text.toString()
            var postNumber = textInputEditTextJoinSellerMainPostNumber.text.toString()
            var postDetail = textInputEditTextJoinSellerMainStoreAddress.text.toString()

            UserRepository.getUserSellerIndex {
                var userindex = it.result.value as Long

                userindex++

//                val fileName = if (uploadUri == null) {
//                    "None"
//                } else {
//                    "imge/img_${System.currentTimeMillis()}.jpg"
//                }

                //베너 구현필요 -> 지금은 null로 처리 (이미지 불러오고 가져오는 function 필요!)
                val userinfo = UserSellerInfo(userindex,0,email,pw,nickNames,null,storeName,storeInfo,postNumber,postDetail)

                UserRepository.setUserSellerInfo(userinfo){
                    UserRepository.setUserSellerIdx(userindex){
                        mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
                    }
                }
            }
        }
    }



}