package com.test.keepgardeningproject_seller.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class AuctionRepository {

    companion object {
        // 해당 상품의 전체 입찰 정보 가져오기
        fun getAuctionProductInfoAll(auctionProductIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("Auction")
            auctionProductIdxRef.orderByChild("auctionAuctionProductIndex").equalTo(auctionProductIdx.toDouble()).get().addOnCompleteListener(callback1)
        }
    }
}