package com.test.keepgardeningproject_customer.UI.MyPageCustomerModify

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.UserRepository

class MyPageCustomerModifyViewModel : ViewModel() {


    val newNicknameData = MutableLiveData<String>()
    val newPasswordData = MutableLiveData<String>()
    val newPasswordCheckData = MutableLiveData<String>()



    
    fun reset(){
        newNicknameData.value = ""
        newPasswordData.value = ""
        newPasswordCheckData.value = ""
    }


}

