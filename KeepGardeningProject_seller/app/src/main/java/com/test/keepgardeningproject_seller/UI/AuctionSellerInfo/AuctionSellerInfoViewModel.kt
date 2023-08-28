package com.test.keepgardeningproject_seller.UI.AuctionSellerInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.AuctionClass
import com.test.keepgardeningproject_seller.Repository.AuctionRepository

class AuctionSellerInfoViewModel : ViewModel() {

    // 전체 상품 목록
    var auctionClassList = MutableLiveData<MutableList<AuctionClass>>()

    init {
        auctionClassList.value = mutableListOf<AuctionClass>()
    }

    fun getAuctionInfo(auctionProductIdx: Long) {

        val tempList = mutableListOf<AuctionClass>()


        AuctionRepository.getAuctionProductInfoAll(auctionProductIdx) {
            for (c1 in it.result.children) {
                val auctionIdx = c1.child("auctionIdx").value as Long
                val auctionProductIdx = c1.child("auctionAuctionProductIndex").value as Long
                var auctionBidNickname = c1.child("auctionBidNickname").value as String
                var auctionBidPrice = c1.child("auctionBidPrice").value as String
                var auctionCustomerIdx = c1.child("auctionCustomerIdx").value as Long

                val a1 = AuctionClass(
                    auctionIdx,
                    auctionProductIdx,
                    auctionBidNickname,
                    auctionBidPrice,
                    auctionCustomerIdx)

                tempList.add(a1)
            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()

            auctionClassList.value = tempList
        }
    }
}