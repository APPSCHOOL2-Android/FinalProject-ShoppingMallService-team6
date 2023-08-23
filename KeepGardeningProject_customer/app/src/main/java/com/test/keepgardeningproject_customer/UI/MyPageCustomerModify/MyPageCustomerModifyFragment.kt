package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerModifyBinding
import java.time.Duration

class MyPageCustomerModifyFragment : Fragment() {

    lateinit var myPageCustomerModifyBinding: FragmentMyPageCustomerModifyBinding
    lateinit var myPageCustomerModifyViewModel: MyPageCustomerModifyViewModel
    lateinit var mainActivity :MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageCustomerModifyBinding = FragmentMyPageCustomerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        myPageCustomerModifyViewModel = ViewModelProvider(mainActivity)[MyPageCustomerModifyViewModel::class.java]

        myPageCustomerModifyViewModel.run {
            newNicknameData.observe(mainActivity){
                myPageCustomerModifyBinding.textInputEditTextMcNickName.setText(it)
            }
            newPasswordData.observe(mainActivity){
                myPageCustomerModifyBinding.textInputEditTextMcPassword.setText(it)
            }
        }

        myPageCustomerModifyBinding.run {

            //번들받아서 이메일이면 비밀번호,확인,닉네임을 나타내고 나머지면 닉네임만 나타내게함

            toolbarMc.run {
                setTitle("정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    //마이메이지 메인화면으로 이동
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                }
            }
            buttonMcModify.run {

                setOnClickListener {
                    //마이페이지 메인화면으로 이동
                    UserRepository.getUserIndex {

                        var idx = it.result.value as Long
                        myPageCustomerModifyViewModel.changeData(idx!!)
                    }
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_MODIFY_FRAGMENT)
                }
            }
        }

        return  myPageCustomerModifyBinding.root
    }


}