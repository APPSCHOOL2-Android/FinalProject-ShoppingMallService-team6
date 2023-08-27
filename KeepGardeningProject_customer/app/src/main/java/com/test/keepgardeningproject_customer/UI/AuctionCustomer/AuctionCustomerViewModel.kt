package com.test.keepgardeningproject_customer.UI.AuctionCustomer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.AuctionProductInfo
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository

class AuctionCustomerViewModel : ViewModel() {
    // 전체 경매상품 목록
    var auctionProductInfoList = MutableLiveData<MutableList<AuctionProductInfo>>()
    var auctionProductImageNameList = MutableLiveData<MutableList<String>>()

    init {
        auctionProductInfoList.value = mutableListOf<AuctionProductInfo>()
        auctionProductImageNameList.value = mutableListOf<String>()
    }


    fun getAuctionProductInfoAll() {
        val tempList = mutableListOf<AuctionProductInfo>()
        val tempImageNameList = mutableListOf<String>()

        AuctionProductRepository.getAuctionRroductAll {
            for (c1 in it.result.children) {
                val auctionProductIdx = c1.child("auctionProductIdx").value as Long
                var auctionProductName = c1.child("auctionProductName").value as String
                var auctionProductDetail = c1.child("auctionProductDetail").value as String
                var auctionProductStoreIdx = c1.child("auctionProductStoreIdx").value as Long

                var auctionProductOpenPrice = c1.child("auctionProductOpenPrice").value as String
                var auctionProductOpenDate = c1.child("auctionProductOpenDate").value as String
                var auctionProductCloseDate = c1.child("auctionProductCloseDate").value as String

                var auctionProductImageList =
                    c1.child("auctionProductImageList").value as ArrayList<String>

                val p1 = AuctionProductInfo(
                    auctionProductIdx,
                    auctionProductName,
                    auctionProductDetail,
                    auctionProductStoreIdx,
                    auctionProductOpenPrice,
                    auctionProductOpenDate,
                    auctionProductCloseDate,
                    auctionProductImageList
                )

                tempList.add(p1)
                tempImageNameList.add(auctionProductImageList[0])
            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()
            tempImageNameList.reverse()

            auctionProductInfoList.value = tempList
            auctionProductImageNameList.value = tempImageNameList
        }
    }
}