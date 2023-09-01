package com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.TotalOrderRepository

class OrderCheckFormCustomerViewModel : ViewModel() {
    var orderCheckFormOrderList = MutableLiveData<MutableList<OrderCheckFormList>>()


    var orderCheckFormTotalOrderIdx = MutableLiveData<Long>()
    var orderCheckFormDate = MutableLiveData<String>()
    var orderCheckFormReceiverName = MutableLiveData<String>()
    var orderCheckFormReceiverPhone = MutableLiveData<String>()
    var orderCheckFormAddress = MutableLiveData<String>()
    var orderCheckFormDeliveryRequest = MutableLiveData<String>()
    var orderCheckFormOrdererName = MutableLiveData<String>()
    var orderCheckFormOrdererPhone = MutableLiveData<String>()
    var orderCheckFormOrdererEmail = MutableLiveData<String>()

    init {
        orderCheckFormOrderList.value = mutableListOf<OrderCheckFormList>()
    }

    fun resetList() {
        orderCheckFormOrderList.value = mutableListOf<OrderCheckFormList>()
    }

    // 주문 정보를 가져온다.
    fun getOrderInfo(totalOrderIdx: Long) {
        val temp1 = mutableListOf<OrderCheckFormList>()

        // 전체 주문 정보 가져오기
        TotalOrderRepository.getOrdersbyTotalOrderIdx(totalOrderIdx) {
            Log.i("s222", "getOrder")
            for (c1 in it.result.children) {
                val ordersProductIdx = c1.child("ordersProductIdx").value as Long
                val ordersProductCount = c1.child("ordersProductCount").value as Long
                val ordersProductPrice = c1.child("ordersProductPrice").value as Long
                val ordersDeliveryState = c1.child("ordersDeliveryState").value as String

                // 상품 인덱스를 통해 상품 정보 가져오기
                ProductRepository.getProductInfoByIdx(ordersProductIdx.toDouble()) {
                    Log.i("s222", "getp")
                    for (p1 in it.result.children) {
                        var productImageList =
                            p1.child("productImageList").value as ArrayList<String>?
                        var productName = p1.child("productName").value as String
                        var productPrice = p1.child("productPrice").value as String

                        val orderCheckFormList = OrderCheckFormList(
                            0L,
                            ordersDeliveryState,
                            productName,
                            ordersProductCount,
                            productPrice,
                            ordersProductPrice.toString(),
                            productImageList?.get(0)!!
                        )
                        temp1.add(orderCheckFormList)
                    }
                    orderCheckFormOrderList.value = temp1
                    Log.i("s222", orderCheckFormOrderList.value.toString())
                }
            }
        }
    }


    // 배송지 정보와 주문자 정보를 가져온다.
    fun getDeliveryAndOrdererInfo(totalOrderIdx: Long) {
        TotalOrderRepository.getTotalOrder(totalOrderIdx) {
            for (c1 in it.result.children) {
                orderCheckFormTotalOrderIdx.value = totalOrderIdx
                orderCheckFormDate.value = c1.child("totalOrderDate").value as String
                orderCheckFormReceiverName.value =
                    c1.child("totalOrderReceiverName").value as String
                orderCheckFormReceiverPhone.value =
                    c1.child("totalOrderReceiverPhone").value as String
                orderCheckFormAddress.value =
                    "${c1.child("totalOrderReceiverAddress").value as String} ${c1.child("totalOrderReceiverAddressDetail").value as String}"
                orderCheckFormDeliveryRequest.value =
                    c1.child("totalOrderDeliveryRequest").value as String
                orderCheckFormOrdererName.value = MainActivity.loginedUserInfo.userNickname!!
                orderCheckFormOrdererPhone.value =
                    c1.child("totalOrderOrdererPhone").value as String
                orderCheckFormOrdererEmail.value = MainActivity.loginedUserInfo.userEmail!!
            }
        }
    }

    // 주문확인서에 주문 목록을 표시하기 위한 클래스
    data class OrderCheckFormList(
        val orderCheckFormListIdx: Long,
        val orderCheckFormListdeliveryState: String,
        val orderCheckFormListproductName: String,
        val orderCheckFormListOption: Long,
        val orderCheckFormListOrderPrice: String,
        val orderCheckFormListProductPrice: String,
        val orderCheckFormListImage: String
    )
}