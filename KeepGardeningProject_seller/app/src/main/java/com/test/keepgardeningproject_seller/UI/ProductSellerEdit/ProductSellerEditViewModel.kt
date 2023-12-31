package com.test.keepgardeningproject_seller.UI.ProductSellerEdit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.UI.ProductSellerEdit.ProductSellerEditFragment.Companion.originImageNum
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ProductSellerEditViewModel : ViewModel() {
    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()
    var productCategory = MutableLiveData<String>()
    var productDetail = MutableLiveData<String>()
    var productMainImage = MutableLiveData<Bitmap>()

    // 상품 이미지 이름 리스트
    var productImageNameList = MutableLiveData<MutableList<String>>()
    var productImageUriList = MutableLiveData<MutableList<Uri>>()

    init {
        productImageNameList.value = mutableListOf<String>()
        productImageUriList.value = mutableListOf<Uri>()
    }


    fun getProductInfo(productIdx: Long) {
        val tempImageNameList = mutableListOf<String>()
        val tempImageUriList = mutableListOf<Uri>()


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

//            // 가장 마지막에 등록한것부터 보여주기
//            tempImageNameList.reverse()

            productImageNameList.value = tempImageNameList

            ProductRepository.getProductImage(productImageNameList.value!![0]) {
                thread {
                    // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                    val url = URL(it.result.toString())
                    // 접속한다.
                    val httpURLConnection = url.openConnection() as HttpURLConnection
                    // 이미지 객체를 생성한다.
                    val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)
                    productMainImage.postValue(bitmap)
                }
            }

            for(fileName in productImageNameList.value!!) {
                ProductRepository.getProductImage(fileName) { uri ->
                    tempImageUriList.add(uri.result)
//                    Log.d("lion","uri list : $tempImageUriList")
                    originImageNum = tempImageUriList.size
                }
            }

            productImageUriList.value = tempImageUriList
//            Log.d("lion","uri list size : ${productImageUriList.value!!.size}")
        }
    }
}