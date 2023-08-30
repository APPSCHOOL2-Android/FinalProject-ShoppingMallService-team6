package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.ReviewRepository

class MyPageCustomerReviewWriteViewModel : ViewModel() {

    var productName = MutableLiveData<String>()
    var storeName = MutableLiveData<String>()

    fun getProductNameByProductIdx(productIdx:Long){

        ReviewRepository.getProductNameByProductIdx(productIdx!!){

            for(c1 in it.result.children){
                productName.value = c1.child("productName").value as String
                break
            }

        }

    }

    fun getStoreNameByStoreIdx(storeIdx:Long){

        ReviewRepository.getStoreNameByStoreIdx(storeIdx){
            for(c1 in it.result.children){
                storeName.value = c1.child("userSellerStoreName").value as String
                break
            }
        }

    }

}