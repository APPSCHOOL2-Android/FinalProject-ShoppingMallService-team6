package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AuctionRepository {

    companion object {
        // 경매 정보 인덱스 가져오기
        fun getAuctionIdx(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val auctionProductIdxRef = database.getReference("AuctionIdx")
            auctionProductIdxRef.get().addOnCompleteListener(callback1)
        }

        // 해당 상품의 전체 입찰 정보 가져오기
        fun getAuctionProductInfoAll(auctionProductIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("Auction")
            auctionProductIdxRef.orderByChild("auctionAuctionProductIndex").equalTo(auctionProductIdx.toDouble()).get().addOnCompleteListener(callback1)
        }
    }
}