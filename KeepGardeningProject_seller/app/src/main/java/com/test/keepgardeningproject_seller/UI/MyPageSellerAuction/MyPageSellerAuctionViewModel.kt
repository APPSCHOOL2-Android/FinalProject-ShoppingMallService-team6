package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.auctionInfo
import com.test.keepgardeningproject_seller.Repository.AuctionSellerDetailRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MyPageSellerAuctionViewModel : ViewModel() {

    var sellerList = MutableLiveData<MutableList<auctionInfo>>()

    init {

        sellerList.value = mutableListOf<auctionInfo>()
    }

    //sellerList 초기화
    fun resetList() {
        sellerList.value = mutableListOf<auctionInfo>()
    }

    fun getData(idx:Long) {
        var myList = mutableListOf<auctionInfo>()
        AuctionSellerDetailRepository.getAuctionSellerDetailAll(idx) {
            for (c1 in it.result.children) {
                var newname = c1.child("auctionProductName").value.toString()
                var newimg = c1.child("auctionProductImageList").value as ArrayList<String>
                var productIdx = c1.child("auctionProductIdx").value as Long
                var imgone = newimg[0]
                var state = c1.child("auctionProductCloseDate").value.toString()
                var newstate = getTime(state)

                var newclass = auctionInfo(idx, imgone, newname, newstate, productIdx)
                myList.add(newclass)
                myList.reverse()
            }

            sellerList.value = myList


        }
    }
    fun getTime(state: String): String {
        var date: Date = Calendar.getInstance().time
        date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(state)
        //현재시간
        var today = Calendar.getInstance()
        //경매종료시간 -현재시간
        var calculateDate = (date.time - today.time.time)

        if (calculateDate < 0) {
            val state = "경매완료"
            return state
        } else {
            val state = "경매중"
            return state
        }
    }

}





