package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HomeCustomerMainHomeViewModel : ViewModel(){

    // 전체 게시글 목록
    var productClassList = MutableLiveData<MutableList<ProductClass>>()
    // 전체 게시글 이미지 목록, 홈화면에서는 첫번째 인덱스 사진 1장씩만 순서대로 저장
    var productThumbnailList = MutableLiveData<MutableList<Bitmap>>()
    
    init { 
        productClassList.value = mutableListOf<ProductClass>()
        productThumbnailList.value = mutableListOf<Bitmap>()
    }

    // 추천상품
    fun getProductInfoAll(){
        val tempList = mutableListOf<ProductClass>()
        val tempImageBitmapList = mutableListOf<Bitmap>()

        ProductRepository.getProductInfoAll {
            for(c1 in it.result.children){
                val productIdx = c1.child("productIdx").value as Long
                var productImageList = c1.child("productImageList").value as ArrayList<String>
                var productName = c1.child("productName").value as String
                var productPrice= c1.child("productPrice").value as String
                var productStoreIdx= c1.child("productStoreIdx").value as Long
                var productCategory= c1.child("productCategory").value as String
                var productDetail= c1.child("productDetail").value as String
                
                val p1 = ProductClass(productIdx, productImageList, productName, productPrice, productStoreIdx, productCategory, productDetail)
                tempList.add(p1)

            }
            
            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()
            
            // mutablelivedata에 담기
            productClassList.value = tempList
        }
    }
}