package com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.OrdersProductClass
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.TotalOrderRepository

class OrderCheckFormCustomerViewModel : ViewModel() {
    var orderCheckFormOrderList = MutableLiveData<MutableList<OrdersProductClass>>()
    var orderCheckFormOrderImageList = MutableLiveData<MutableList<String>>()
    var orderCheckFormOrderProductList = MutableLiveData<MutableList<ProductClass>>()

    var orderCheckFormTotalOrderIdx = MutableLiveData<Long>()
    var orderCheckFormDate = MutableLiveData<String>()
    var orderCheckFormReceiverName = MutableLiveData<String>()
    var orderCheckFormReceiverPhone = MutableLiveData<String>()
    var orderCheckFormAddress = MutableLiveData<String>()
    var orderCheckFormDeliveryRequest = MutableLiveData<String>()
    var orderCheckFormOrdererName = MutableLiveData<String>()
    var orderCheckFormOrdererPhone = MutableLiveData<String>()
    var orderCheckFormOrdererEmail = MutableLiveData<String>()

//    init {
//        orderCheckFormOrderList.value = mutableListOf<OrdersProductClass>()
//        orderCheckFormOrderImageList.value = mutableListOf<String>()
//        orderCheckFormOrderProductList.value = mutableListOf<ProductClass>()
//    }

    // 주문 정보를 가져온다.
    fun getOrderInfo(totalOrderIdx: Long) {
        val tempOrderList = mutableListOf<OrdersProductClass>()
        val tempImageList = mutableListOf<String>()
        val tempProductList = mutableListOf<ProductClass>()
        var tempProductList2 = mutableListOf<ProductClass>()

        // 전체 주문 정보 가져오기
        TotalOrderRepository.getOrdersbyTotalOrderIdx(totalOrderIdx) {
            Log.i("s222", "getOrder")
            for (c1 in it.result.children) {
                val ordersIdx = c1.child("ordersIdx").value as Long
                val ordersCustomerIdx = c1.child("ordersCustomerIdx").value as Long
                val ordersProductIdx = c1.child("ordersProductIdx").value as Long
                val ordersProductCount = c1.child("ordersProductCount").value as Long
                val ordersProductPrice = c1.child("ordersProductPrice").value as Long
                val ordersDeliveryState = c1.child("ordersDeliveryState").value as String
                val ordersTotalOrderIdx = c1.child("ordersTotalOrderIdx").value as Long
                val ordersProductImage = c1.child("ordersProductImage").value as String

                val ordersProductClass = OrdersProductClass(
                    ordersIdx,
                    ordersCustomerIdx,
                    ordersProductIdx,
                    ordersProductCount,
                    ordersProductPrice,
                    ordersDeliveryState,
                    ordersTotalOrderIdx,
                    ordersProductImage
                )

                tempOrderList.add(ordersProductClass)
                Log.i("s222", tempOrderList.toString())
                // 상품 인덱스를 통해 상품 정보 가져오기
                ProductRepository.getProductInfoByIdx(ordersProductIdx.toDouble()) {
                    Log.i("s222", "getp")
                    for (p1 in it.result.children) {
                        var productIdx = p1.child("productIdx").value as Long
                        var productImageList = p1.child("productImageList").value as ArrayList<String>?
                        var productName = p1.child("productName").value as String
                        var productPrice = p1.child("productPrice").value as String
                        var productStoreIdx = p1.child("productStoreIdx").value as Long
                        var productCategory = p1.child("productCategory").value as String
                        var productDetail = p1.child("productDetail").value as String

                        val productClass = ProductClass(
                            productIdx,
                            productImageList,
                            productName,
                            productPrice,
                            productStoreIdx,
                            productCategory,
                            productDetail
                        )

                        tempProductList.add(productClass)
                        Log.i("s222", "tempProductList : ${tempProductList.toString()}")
                        tempImageList.add(productImageList?.get(0)!!)
                    }
                    orderCheckFormOrderProductList.value = tempProductList
                    orderCheckFormOrderImageList.value = tempImageList
                }
            }
            orderCheckFormOrderList.value = tempOrderList
        }
    }


    // 배송지 정보와 주문자 정보를 가져온다.
    fun getDeliveryAndOrdererInfo(totalOrderIdx: Long) {
        TotalOrderRepository.getTotalOrder(totalOrderIdx) {
            for (c1 in it.result.children) {
                orderCheckFormTotalOrderIdx.value = totalOrderIdx
                orderCheckFormDate.value = c1.child("totalOrderDate").value as String
                orderCheckFormReceiverName.value = c1.child("totalOrderReceiverName").value as String
                orderCheckFormReceiverPhone.value = c1.child("totalOrderReceiverPhone").value as String
                orderCheckFormAddress.value =
                    "${c1.child("totalOrderReceiverAddress").value as String} ${c1.child("totalOrderReceiverAddressDetail").value as String}"
                orderCheckFormDeliveryRequest.value = c1.child("totalOrderDeliveryRequest").value as String
                orderCheckFormOrdererName.value = MainActivity.loginedUserInfo.userNickname!!
                orderCheckFormOrdererPhone.value = c1.child("totalOrderOrdererPhone").value as String
                orderCheckFormOrdererEmail.value = MainActivity.loginedUserInfo.userEmail!!
            }
        }
    }
}