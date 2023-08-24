package com.test.keepgardeningproject_seller.UI.HomeSeller

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.AuctionProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository

class HomeSellerViewModel : ViewModel() {
    // 전체 상품 목록
    var productClassList = MutableLiveData<MutableList<ProductClass>>()
    // 상품 이미지 이름 리스트
    var productImageNameList = MutableLiveData<MutableList<String>>()

    init {
        productClassList.value = mutableListOf<ProductClass>()
        productImageNameList.value = mutableListOf<String>()
    }


    fun getProductInfoAll(storeIdx: Long) {
        val tempList = mutableListOf<ProductClass>()
        val tempImageNameList = mutableListOf<String>()
        val tempUriList = mutableListOf<Uri>()

        ProductRepository.getProductInfoAll(storeIdx) {
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

            productClassList.value = tempList
            productImageNameList.value = tempImageNameList
        }
    }


    // 전체 상품 목록
    var auctionProductClassList = MutableLiveData<MutableList<AuctionProductClass>>()
    // 상품 이미지 이름 리스트
    var auctionProductImageNameList = MutableLiveData<MutableList<String>>()

    init {
        auctionProductClassList.value = mutableListOf<AuctionProductClass>()
        auctionProductImageNameList.value = mutableListOf<String>()
    }


    fun getAuctionProductInfoAll(storeIdx: Long) {
        val tempList = mutableListOf<AuctionProductClass>()
        val tempImageNameList = mutableListOf<String>()
        val tempUriList = mutableListOf<Uri>()

        AuctionProductRepository.getAuctionProductInfoAll(storeIdx) {
            for (c1 in it.result.children) {
                val auctionProductIdx = c1.child("auctionProductIdx").value as Long
                var auctionProductImageList = c1.child("auctionProductImageList").value as ArrayList<String>
                var auctionProductName = c1.child("auctionProductName").value as String
                var auctionProductOpenDate = c1.child("auctionProductOpenDate").value as String
                var auctionProductCloseDate = c1.child("auctionProductCloseDate").value as String
                var auctionProductOpenPrice = c1.child("auctionProductOpenPrice").value as String
                var auctionProductStoreIdx = c1.child("auctionProductStoreIdx").value as Long
                var auctionProductDetail = c1.child("auctionProductDetail").value as String


                val p1 = AuctionProductClass(
                    auctionProductIdx,
                    auctionProductImageList,
                    auctionProductName,
                    auctionProductOpenPrice,
                    auctionProductStoreIdx,
                    auctionProductOpenDate,
                    auctionProductCloseDate,
                    auctionProductDetail
                )
                tempList.add(p1)
                tempImageNameList.add(auctionProductImageList[0])
            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()
            tempImageNameList.reverse()

            auctionProductClassList.value = tempList
            auctionProductImageNameList.value = tempImageNameList
        }
    }
}