package com.test.keepgardeningproject_seller.UI.MyPageSellerProductState

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.OrdersProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.Repository.OrderProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository

class MyPageSellerProductStateViewModel : ViewModel() {
    // product
    var productList = MutableLiveData<MutableList<ProductClass>>()
    var productImageList = MutableLiveData<MutableList<String>>()

    // orders
    var orderProductList = MutableLiveData<MutableList<OrdersProductClass>>()

    init {
        productList.value = mutableListOf<ProductClass>()
        productImageList.value = mutableListOf<String>()
        orderProductList.value = mutableListOf<OrdersProductClass>()
    }

    fun getProductAll() {
        val tempList = mutableListOf<ProductClass>()
        val tempImageNameList = mutableListOf<String>()

        ProductRepository.getProductInfoAll {
            for (c1 in it.result.children) {
                val productIdx = c1.child("productIdx").value as Long
                var productImageList = c1.child("productImageList").value as ArrayList<String>
                var productName = c1.child("productName").value as String
                var productPrice = c1.child("productPrice").value as String
                var productStoreIdx = c1.child("productStoreIdx").value as Long
                var productCategory = c1.child("productCategory").value as String
                var productDetail = c1.child("productDetail").value as String

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
                tempImageNameList.add(productImageList[0])
            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()
            tempImageNameList.reverse()

            // mutablelivedata에 담기
            productList.value = tempList
            productImageList.value = tempImageNameList
        }
    }

    fun getOrdersProductAll() {
        val tempList = mutableListOf<OrdersProductClass>()
        OrderProductRepository.getOrdersProductAll {
            for (c1 in it.result.children) {
                val ordersIdx = c1.child("ordersIdx").value as Long
                val ordersCustomerIdx = c1.child("ordersCustomerIdx").value as Long
                var ordersProductIdx = c1.child("ordersProductIdx").value as Long
                var ordersProductCount = c1.child("ordersProductCount").value as Long
                var ordersProductPrice = c1.child("ordersProductPrice").value as Long
                var ordersDeliveryState = c1.child("ordersDeliveryState").value as String
                var ordersTotalOrderIdx = c1.child("ordersTotalOrderIdx").value as Long

                val OP1 = OrdersProductClass(
                    ordersIdx,
                    ordersCustomerIdx,
                    ordersProductIdx,
                    ordersProductCount,
                    ordersProductPrice,
                    ordersDeliveryState,
                    ordersTotalOrderIdx
                )

                tempList.add(OP1)
            }

            tempList.reverse()

            orderProductList.value = tempList
        }
    }

}