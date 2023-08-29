package com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.auctionCustomerDetail
import com.test.keepgardeningproject_customer.DAO.purchaseInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MyPageCustomerAuctionViewModel : ViewModel() {

    var getauctiondetailList = MutableLiveData<MutableList<auctionCustomerDetail>>()

    init {
        getauctiondetailList.value = mutableListOf<auctionCustomerDetail>()
    }

    fun resetList(){
        getauctiondetailList.value  = mutableListOf<auctionCustomerDetail>()
    }
    fun getAll(){

        var idx = MainActivity.loginedUserInfo.userIdx
        var templist = mutableListOf<auctionCustomerDetail>()

        AuctionRepository.getAuctionInfoByUserIdx(idx!!){
            for(c1 in it.result.children){
                var auctionproductidx = c1.child("auctionAuctionProductIndex").value as Long
                var auctionbidPrice =c1.child("auctionBidPrice").value as String
                AuctionProductRepository.getAuctionProductInfo(auctionproductidx){
                    for(c1 in it.result.children){
                        var name = c1.child("auctionProductName").value as String
                        var img = c1.child("auctionProductImageList").value as ArrayList<String>
                        var newimg = img[0]
                        var state = c1.child("auctionProductCloseDate").value as String
                        var newstate = getTime(state)
                        var productidx = c1.child("auctionProductIdx").value as Long

                        var newclass = auctionCustomerDetail(name,newimg,newstate,auctionbidPrice,productidx)
                        Log.d("Lim class","${newclass}")

                        templist.add(newclass)
                        getauctiondetailList.value = templist
                    }
                }
            }


        }

    }
    fun getTime(state: String):String{
        var data : Date = Calendar.getInstance().time
        data = SimpleDateFormat("yyyy/MM/dd HH:mm") .parse(state)
        var today = Calendar.getInstance()

        var caculates = (data.time - today.time.time)
        if(caculates<0){
            val state  = "경매완료"
            return state
        } else {
            val state  = "경매중"
            return state
        }
    }
}









