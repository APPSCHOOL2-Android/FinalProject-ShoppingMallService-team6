package com.test.keepgardeningproject_customer.UI.CartCustomer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.Repository.CartRepository
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class CartCustomerViewModel : ViewModel() {
    var cartCount = MutableLiveData<Int>()
    //var productName = MutableLiveData<String>()
    var productPrice = MutableLiveData<String>()

    var cartList = MutableLiveData<MutableList<CartClass>>()
    var cartImageList = MutableLiveData<MutableList<String>>()

    init {
        cartList.value = mutableListOf<CartClass>()
        cartImageList.value = mutableListOf<String>()
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

    // 장바구니에 있는 상품의 정보 불러오기
    fun getProductInCart(cartUserIdx: Long) {
        val tempList = mutableListOf<CartClass>()
        val tempImageList = mutableListOf<String>()

        CartRepository.getCartbyUserIdx(cartUserIdx) {
            for (c1 in it.result.children) {
                val cartIdx = c1.child("cartIdx").value as Long
                val cartImage = c1.child("cartImage").value as String
                val cartName = c1.child("cartName").value as String
                val cartPrice = c1.child("cartPrice").value as Long
                val cartProductIdx = c1.child("cartProductIdx").value as Long
                val cartUserIdx = c1.child("cartUserIdx").value as Long
                val cartCount = c1.child("cartCount").value as Long

                val cart = CartClass(cartIdx, cartUserIdx, cartProductIdx, cartName, cartPrice, cartCount, cartImage)
                tempList.add(cart)
                tempImageList.add(cartImage)
            }
            cartList.value = tempList
            cartImageList.value = tempImageList
            cartCount.value = cartList.value!!.size
        }
    }

    // 상품 갯수 + 하기
    fun plusCartProduct(cartClass: CartClass) {
        CartRepository.plusCartProduct(cartClass) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 갯수 - 하기
    fun minusCartProduct(cartClass: CartClass) {
        CartRepository.minusCartProduct(cartClass) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 하나 삭제하기
    fun removeCartProduct(cartClass: CartClass) {
        CartRepository.removeCartProduct(cartClass.cartIdx) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 전체 삭제하기
    fun deleteAllCart(userIdx: Long) {
        CartRepository.deleteAllCart(userIdx) {
            getProductInCart(userIdx)
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