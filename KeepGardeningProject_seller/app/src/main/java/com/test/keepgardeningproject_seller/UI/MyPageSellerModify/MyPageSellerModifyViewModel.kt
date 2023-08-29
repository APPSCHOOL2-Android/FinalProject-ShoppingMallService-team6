package com.test.keepgardeningproject_seller.UI.MyPageSellerModify

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageSellerModifyViewModel : ViewModel() {

    val newNickNameData = MutableLiveData<String>()
    var newBannerData = MutableLiveData<Bitmap>()
    val newStoreNameData = MutableLiveData<String>()
    val newStoreDetailData = MutableLiveData<String>()
    val newAddressNumberData = MutableLiveData<String>()
    val newAddressDetailData = MutableLiveData<String>()

    fun reset(){
        newNickNameData.value = ""
        newStoreNameData.value = ""
        newStoreDetailData.value = ""
        newAddressNumberData.value = ""
        newAddressDetailData.value  = ""
    }
}