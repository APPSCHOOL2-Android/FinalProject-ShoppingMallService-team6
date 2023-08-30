package com.test.keepgardeningproject_customer.UI.ProductCustomerQnAWrite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ProductCustomerQnAWriteViewModel():ViewModel() {
    var productName = MutableLiveData<String>()
    var productStoreIdx = MutableLiveData<Long>()
    var productStoreName = MutableLiveData<String>()
    var productMainImage = MutableLiveData<Uri>()

    // 상품 이미지 이름 리스트
    var productImageNameList = MutableLiveData<MutableList<String>>()

    init {
        productImageNameList.value = mutableListOf<String>()
    }


    // 상품 정보 가져오기
    fun getProductInfo(productIdx: Long) {
        val tempImageNameList = mutableListOf<String>()


        ProductRepository.getProductInfoByIdx(productIdx.toDouble()) { it ->
            for (c1 in it.result.children) {
                var productImageList = c1.child("productImageList").value as ArrayList<String>
                productName.value = c1.child("productName").value as String
                productStoreIdx.value = c1.child("productStoreIdx").value as Long

                for (i in 0 until productImageList.size) {
                    tempImageNameList.add(productImageList[i])
                }

//            // 가장 마지막에 등록한것부터 보여주기
//            tempImageNameList.reverse()

                productImageNameList.value = tempImageNameList

                ProductRepository.getProductSellerInfoByIdx(productStoreIdx.value!!.toDouble()) {
                    for (c1 in it.result.children) {
                        productStoreName.value = c1.child("userSellerStoreName").value as String
                        Log.d("lion", "store name : ${productStoreName.value}")
                    }
                }
            }

            ProductRepository.getProductImage(productImageNameList.value!![0]) {
                productMainImage.value = it.result
            }
        }
    }

    var auctionProductName = MutableLiveData<String>()
    var auctionProductStoreIdx = MutableLiveData<Long>()
    var auctionProductStoreName = MutableLiveData<String>()
    var auctionProductMainImage = MutableLiveData<Uri>()

    // 상품 이미지 이름 리스트
    var auctionProductImageNameList = MutableLiveData<MutableList<String>>()

    init {
        auctionProductImageNameList.value = mutableListOf<String>()
    }


    // 경매 상품 정보 가져오기
    fun getAuctionProductInfo(auctionProductIdx: Long) {
        val tempImageNameList = mutableListOf<String>()


        AuctionProductRepository.getAuctionProductByIdx(auctionProductIdx.toDouble()) { it ->
            for (c1 in it.result.children) {
                var auctionProductImageList = c1.child("auctionProductImageList").value as ArrayList<String>
                auctionProductName.value = c1.child("auctionProductName").value as String
                auctionProductStoreIdx.value = c1.child("auctionProductStoreIdx").value as Long

                for (i in 0 until auctionProductImageList.size) {
                    tempImageNameList.add(auctionProductImageList[i])
                }

//            // 가장 마지막에 등록한것부터 보여주기
//            tempImageNameList.reverse()

                auctionProductImageNameList.value = tempImageNameList

                ProductRepository.getProductSellerInfoByIdx(auctionProductStoreIdx.value!!.toDouble()) {
                    for (c1 in it.result.children) {
                        auctionProductStoreName.value = c1.child("userSellerStoreName").value as String
                    }
                }
            }

            ProductRepository.getProductImage(auctionProductImageNameList.value!![0]) {
                auctionProductMainImage.value = it.result
            }
        }
    }
}

