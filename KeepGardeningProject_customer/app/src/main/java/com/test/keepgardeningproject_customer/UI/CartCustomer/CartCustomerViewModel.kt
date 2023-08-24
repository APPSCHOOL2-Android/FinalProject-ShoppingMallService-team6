package com.test.keepgardeningproject_customer.UI.CartCustomer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.Repository.CartRepository
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class CartCustomerViewModel : ViewModel() {
    var cartCount = MutableLiveData<Int>()
    var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()

    var productList = MutableLiveData<MutableList<ProductClass>>()
    var productImageList = MutableLiveData<MutableList<String>>()

    init {
        productList.value = mutableListOf<ProductClass>()
        productImageList.value = mutableListOf<String>()
    }

    // 장바구니에 있는 상품 목록 불러오기
//    fun getProductInCart(productIdxList: MutableList<Long>) {
//        val tempList = mutableListOf<ProductClass>()
//
//        for (idx in productIdxList) {
//            CartRepository.getProductbyIdx(idx) {
//                for (c1 in it.result.children) {
//                    val productIdx = c1.child("productIdx").value as Long
//                    val productImageList = c1.child("productImageList").value as ArrayList<String>
//                    val productName = c1.child("productName").value as String
//                    val productPrice = c1.child("productPrice").value as String
//                    val productStoreIdx = c1.child("productStoreIdx").value as Long
//                    val productCategory = c1.child("productCategory").value as String
//                    val productDetail = c1.child("productDetail").value as String
//
//                    val p1 = ProductClass(
//                        productIdx,
//                        productImageList,
//                        productName,
//                        productPrice,
//                        productStoreIdx,
//                        productCategory,
//                        productDetail
//                    )
//                    tempList.add(p1)
//                }
//                productList.value = tempList
//            }
//        }
//    }

    // 장바구니에 있는 상품과 이미지 파일명 불러오기
    fun getProductInCart(productIdxList: MutableList<Long>) {
        val tempList = mutableListOf<ProductClass>()
        val tempImageList = mutableListOf<String>()

        CartRepository.getProductAll {
            for (c1 in it.result.children) {
                val productIdx = c1.child("productIdx").value as Long
                val productImageList = c1.child("productImageList").value as ArrayList<String>
                val productName = c1.child("productName").value as String
                val productPrice = c1.child("productPrice").value as String
                val productStoreIdx = c1.child("productStoreIdx").value as Long
                val productCategory = c1.child("productCategory").value as String
                val productDetail = c1.child("productDetail").value as String

                if (productIdx !in productIdxList) {
                    continue
                }

                val p1 = ProductClass(
                    productIdx,
                    productImageList,
                    productName,
                    productPrice,
                    productStoreIdx,
                    productCategory,
                    productDetail
                )
                tempList.add(p1)
                tempImageList.add(productImageList[0])
            }
            productList.value = tempList
            productImageList.value = tempImageList
        }
    }

    // 상품 이미지 불러오기
//    fun getProductImageInCart(productIdxList: MutableList<Long>) {
//        val tempList = mutableListOf<Bitmap>()
//
//        CartRepository.getProductAll {
//            for (c1 in it.result.children) {
//                val productIdx = c1.child("productIdx").value as Long
//                val productImageList = c1.child("productImageList").value as ArrayList<String>
//                Log.i("ssss", productImageList.toString())
//
//                if (productIdx !in productIdxList) {
//                    continue
//                }
//
//                if (productImageList[0] != "None") {
//                    Log.i("ssss","Not none")
//                    CartRepository.getProductMainImage(productImageList[0]) {
//                        thread {
//                            // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
//                            val url = URL(it.result.toString())
//                            // 접속한다.
//                            val httpURLConnection = url.openConnection() as HttpURLConnection
//                            // 이미지 객체를 생성한다.
//                            val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)
//                            tempList.add(bitmap)
//                            Log.i("ssss", tempList.size.toString())
//                        }
//                    }
//                }
//            }
//            productBitmapList.value = tempList
//    }
}