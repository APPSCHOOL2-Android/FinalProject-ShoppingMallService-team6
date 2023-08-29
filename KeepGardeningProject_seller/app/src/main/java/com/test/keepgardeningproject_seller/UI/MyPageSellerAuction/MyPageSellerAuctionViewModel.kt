package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import android.os.Build.VERSION_CODES.M
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.play.integrity.internal.m
import com.test.keepgardeningproject_seller.DAO.UserDAO
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.DAO.auctionInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.AuctionSellerDetailRepository
import com.test.keepgardeningproject_seller.Repository.UserRepository

class MyPageSellerAuctionViewModel : ViewModel() {

    var sellerList = MutableLiveData<MutableList<auctionInfo>>()

    init {

        sellerList.value = mutableListOf<auctionInfo>()
    }

    //sellerList 초기화
    fun resetList() {
        sellerList.value = mutableListOf<auctionInfo>()
    }

    fun getPostALl(idx: Long) {
        var myList = mutableListOf<auctionInfo>()
        AuctionSellerDetailRepository.getAuctionSellerDetailAll(idx) {
            for (c1 in it.result.children) {
                var newName = c1.child("auctionDetailTitle").value as String
                var newImg = c1.child("auctionDetailImg").value as String
                var newState = c1.child("auctionDetailState").value as String
                var newidxOne = c1.child("auctionDetailIdx").value as Long
                var productidx = c1.child("auctionProductIdx").value as Long
                var productInfoidx = c1.child("auctionDetailInfoIdx").value as Long
                var newclass2 = auctionInfo(newidxOne, newImg, newName, newState, productidx,productInfoidx)
                myList.add(newclass2)
            }
            sellerList.value = myList
        }
    }

}





