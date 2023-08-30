package com.test.keepgardeningproject_customer.UI.StoreInfoCustomer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.UserSellerInfo
import com.test.keepgardeningproject_customer.Repository.StoreRepository

class StoreInfoCustomerViewModel : ViewModel() {
    var storeInfoStoreList = MutableLiveData<MutableList<UserSellerInfo>>()
    var storeInfoImageList = MutableLiveData<MutableList<String>>()

    init {
        storeInfoStoreList.value = mutableListOf<UserSellerInfo>()
        storeInfoImageList.value = mutableListOf<String>()
    }
    // 모든 스토어 데이터 불러오기.
    fun getAllStoreInfo() {
        var tempList = mutableListOf<UserSellerInfo>()
        var tempImageList = mutableListOf<String>()

        StoreRepository.getAllStoreInfo {
            for (c1 in it.result.children) {
                val userSellerIdx = c1.child("userSellerIdx").value as Long?
                val userSellerLoginType = c1.child("userSellerLoginType").value as Long?
                val userSellerEmail = c1.child("userSellerEmail").value as String?
                val userSellerPw = c1.child("userSellerPw").value as String?
                val userSellerNickname = c1.child("userSellerNickname").value as String?
                val userSellerBanner = c1.child("userSellerBanner").value as String?
                val userSellerStoreName = c1.child("userSellerStoreName").value as String?
                val userSellerStoreInfo = c1.child("userSellerStoreInfo").value as String?
                val userSellerPostNumber = c1.child("userSellerPostNumber").value as String?
                val userSellerPostDetail = c1.child("userSellerPostDetail").value as String?

                val userSellerInfo = UserSellerInfo(
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

                tempList.add(userSellerInfo)
                tempImageList.add(userSellerBanner!!)
                Log.i("f222", tempImageList.toString())
            }
            storeInfoStoreList.value = tempList
            storeInfoImageList.value = tempImageList
        }
    }

}