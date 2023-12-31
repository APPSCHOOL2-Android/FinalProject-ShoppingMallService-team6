package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository

class HomeCustomerMainHomeViewModel : ViewModel() {

    // 전체 게시글 목록
    var productClassList = MutableLiveData<MutableList<ProductClass>>()
    // 게시글 이미지 이름 리스트
    var productImageNameList = MutableLiveData<MutableList<String>>()

    // 인기상품 idx 리스트
    var ordersProductIdxList = MutableLiveData<MutableList<Long>>()

    init {
        productClassList.value = mutableListOf<ProductClass>()
        productImageNameList.value = mutableListOf<String>()
        ordersProductIdxList.value = mutableListOf<Long>()
    }

    // 추천상품
    fun getProductInfoAll() {
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
            productClassList.value = tempList
            productImageNameList.value = tempImageNameList
        }
    }

    fun getOrdersInfoAll(){
        val tempList = mutableListOf<Long>()
        OrderProductRepository.getOrdersProductAll {
            for(c1 in it.result.children){
                val ordersProductIdx = c1.child("ordersProductIdx").value as Long

                tempList.add(ordersProductIdx)
            }

            ordersProductIdxList.value = tempList
        }
    }

}