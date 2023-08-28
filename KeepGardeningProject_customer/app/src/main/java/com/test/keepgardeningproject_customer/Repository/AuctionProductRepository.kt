package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class AuctionProductRepository {

    companion object{
        //인덱스 가져옴
        fun getAuctionProductIndex(callback1: (it: Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionProductIdx")
            databaseRef.get().addOnCompleteListener (callback1)
        }


        //인덱스를 통해 상품정보 가져옴
        fun getAuctionProductInfo(auctionProductIdx:Long,callback1: (it: Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionProduct")
            databaseRef.orderByChild("auctionProductIdx").equalTo(auctionProductIdx.toDouble()!!).get().addOnCompleteListener(callback1)
        }
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




