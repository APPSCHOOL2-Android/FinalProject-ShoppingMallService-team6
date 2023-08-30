package com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MyPageCustomerAuctionViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var getauctiondetailList = MutableLiveData<MutableList<auctionCustomerDetail>>()

    init {
        getauctiondetailList.value = mutableListOf<auctionCustomerDetail>()
    }
    fun getData(){


        //이미지,상태,이름

        //내가고른 경매상품의 인덱스

        //AuctionDA0 - auctionCustomerList(useridx)

        //AuctionProduct
        // -> auctionProductName(이름),auctionImageList(이미지),auctionCloseState(상태),



        //userSellerIdx = auctionProductstoreIdx(판매자 idx)

        //useridx == auctioncustomeridx
        //auctionAuctionProductIndex = auctionProductIdx
        var idx = MainActivity.loginedUserInfo.userIdx

        //useridx가져옴
        Log.d("Lim useridx","${idx}")

        var templist = mutableListOf<auctionCustomerDetail>()

        //내가 입찰한 상품들을 가져와야 함 - 경매중과 경매종료를 나누기 위해
        AuctionRepository.getAuctionInfoByUserIdx(idx!!){
            for(c1 in it.result.children){
                var auctionproductidx = c1.child("auctionAuctionProductIndex").value as Long
                AuctionProductRepository.getAuctionProductInfo(auctionproductidx){
                    for(c1 in it.result.children){
                        var name = c1.child("auctionProductName").value as String
                        var img = c1.child("auctionProductImageList").value as ArrayList<String>
                        var newimg = img[0]
                        var state = c1.child("auctionProductCloseDate").value as String
                        var newstate = getTime(state)

                        var newclass = auctionCustomerDetail(name,newimg,newstate)
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









