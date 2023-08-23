package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.UserRepository

class MyPageCustomerModifyViewModel : ViewModel() {


    val newNicknameData = MutableLiveData<String>()
    val newPasswordData = MutableLiveData<String>()







    //닉네임 변경
    fun changeData(changeidx:Long?){
        //번들로 사용자 인덱스 를 받고 인덱스를 통해서 닉네임변경
        UserRepository.getUserInfoByUserIdx(changeidx) {
            for(c1 in it.result.children){

                newNicknameData.value = c1.child("userNickname").value as String


                Log.d("Lim's","${newNicknameData.value}")
            }
        }
    }
}

