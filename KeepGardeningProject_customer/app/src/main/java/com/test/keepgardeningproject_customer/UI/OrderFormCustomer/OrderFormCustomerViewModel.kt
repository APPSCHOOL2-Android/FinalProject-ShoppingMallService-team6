package com.test.keepgardeningproject_customer.UI.OrderFormCustomer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository

class OrderFormCustomerViewModel : ViewModel() {
    var orderFormProductList = MutableLiveData<MutableList<CartClass>>()
    var orderFormProductImageList = MutableLiveData<MutableList<String>>()

    var orderFormTotalProductPrice = MutableLiveData<Long>()
    var orderFormDeliveryFee = MutableLiveData<Long>()
    var orderFormTotalPrice = MutableLiveData<Long>()
    var orderFormOrdererName = MutableLiveData<String>()
    var orderFormOrdererEmail = MutableLiveData<String>()

    init {
        orderFormProductList.value = mutableListOf<CartClass>()
        orderFormProductImageList.value = mutableListOf<String>()
    }

    // 장바구니에서 상품의 정보 불러오기 (장바구니 화면 -> 주문서 화면)
    fun getProductFromCart(cartUserIdx: Long) {
        val tempList = mutableListOf<CartClass>()
        val tempImageList = mutableListOf<String>()

        OrderProductRepository.getCartbyUserIdx(cartUserIdx) {
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
            orderFormProductList.value = tempList
            orderFormProductImageList.value = tempImageList

            getPaymentPrice()
            getOrdererInfo()
        }
    }

    // 상품 페이지에서 상품 정보 불러오기 (상품 화면 -> 주문서 화면)
    fun getProductFromProduct(productIdx: Long, count: Long) {
        val tempList = mutableListOf<CartClass>()
        val tempImageList = mutableListOf<String>()

        ProductRepository.getProductInfoByIdx(productIdx.toDouble()) {
            for (c1 in it.result.children) {
                val cartIdx = 0L
                val cartImage = c1.child("productImage").value as String
                val cartName = c1.child("productName").value as String
                val cartPrice = c1.child("productPrice").value as Long
                val cartProductIdx = c1.child("ProductIdx").value as Long
                val cartUserIdx = MainActivity.loginedUserInfo.userIdx!!
                val cartCount = count

                val cart = CartClass(cartIdx, cartUserIdx, cartProductIdx, cartName, cartPrice, cartCount, cartImage)
                tempList.add(cart)
                tempImageList.add(cartImage)
            }
            orderFormProductList.value = tempList
            orderFormProductImageList.value = tempImageList
        }
    }

    // 결제 금액 불러오기
    fun getPaymentPrice() {
        var productPrice = 0L
        var deliveryFee = 0L
        var totalPrice = 0L

        for (product in orderFormProductList.value!!) {
            productPrice += product.cartPrice
            deliveryFee += 3000L
        }

        totalPrice = productPrice + deliveryFee

        orderFormTotalProductPrice.value = productPrice
        orderFormDeliveryFee.value = deliveryFee
        orderFormTotalPrice.value = totalPrice
    }

    // 주문자 정보 불러오기
    fun getOrdererInfo() {
        orderFormOrdererName.value = MainActivity.loginedUserInfo.userNickname!!
        orderFormOrdererEmail.value = MainActivity.loginedUserInfo.userEmail!!
    }
}