package com.test.keepgardeningproject_customer.UI.JoinCustomerMain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinCustomerMainViewModel() : ViewModel() {

    var userEmail = MutableLiveData<String>()
    var userPw = MutableLiveData<String>()
    var userNickname = MutableLiveData<String>()
    var userLoginType = MutableLiveData<Int>()
}