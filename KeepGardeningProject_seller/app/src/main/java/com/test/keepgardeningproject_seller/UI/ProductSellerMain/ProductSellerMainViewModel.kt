package com.test.keepgardeningproject_seller.UI.ProductSellerMain

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.Repository.ProductRepository

class ProductSellerMainViewModel : ViewModel() {

    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()
    var productCategory = MutableLiveData<String>()
    var productDetail = MutableLiveData<String>()
    var productMainImage = MutableLiveData<Uri>()

    // 상품 이미지 이름 리스트
    var productImageNameList = MutableLiveData<MutableList<String>>()

    init {
        productImageNameList.value = mutableListOf<String>()
    }


    fun getProductInfo(productIdx: Long) {
        val tempImageNameList = mutableListOf<String>()


        ProductRepository.getProductInfoByIdx(productIdx) { it ->
            for (c1 in it.result.children) {
                var productImageList = c1.child("productImageList").value as ArrayList<String>
                productName.value = c1.child("productName").value as String
                productPrice.value = c1.child("productPrice").value as String
                productCategory.value = c1.child("productCategory").value as String
                productDetail.value = c1.child("productDetail").value as String

                for (i in 0 until productImageList.size) {
                    tempImageNameList.add(productImageList[i])
                }
            }

            productImageNameList.value = tempImageNameList

            ProductRepository.getProductImage(productImageNameList.value!![0]) {
                productMainImage.value = it.result
            }
        }
    }
}