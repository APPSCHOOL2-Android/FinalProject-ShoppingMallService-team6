package com.test.keepgardeningproject_seller.UI.LoginSellerMain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.UserClass

class LoginSellerMainViewModel : ViewModel() {
    var userLoginType = MutableLiveData<String>()
    var userEmail = MutableLiveData<String>()
    var userPw = MutableLiveData<String?>()
    var userPw2 = MutableLiveData<String?>()
    var userNickName = MutableLiveData<String>()

    var storeIndex = MutableLiveData<Long?>()
    var storeName = MutableLiveData<String>()
    var storeDetail = MutableLiveData<String>()
    var storeImageTitle = MutableLiveData<String?>()
    var storePostAddress = MutableLiveData<String?>()
    var storePostAddressDetail = MutableLiveData<String?>()
    fun reset(){
        userEmail.value = ""
        userPw.value = ""
        userPw2.value = ""
    }
}