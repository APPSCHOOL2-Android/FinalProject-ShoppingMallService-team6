package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class AuctionProductRepository {
    companion object{
        fun getAuctionRroductAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val auctionProductRef = database.getReference("AuctionProduct")
            auctionProductRef.orderByChild("auctionProductIdx").get().addOnCompleteListener(callback1)
        }

        fun getAuctionProductByIdx(auctionProductIdx : Double, callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val auctionProductRef = database.getReference("AuctionProduct")
            auctionProductRef.orderByChild("auctionProductIdx").equalTo(auctionProductIdx).get().addOnCompleteListener(callback1)
        }
    }
}