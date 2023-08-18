package com.test.keepgardeningproject_customer.UI.AlertCustomer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlertCustomerViewModel : ViewModel() {
    var alertDataList = MutableLiveData<MutableList<String>>()
}