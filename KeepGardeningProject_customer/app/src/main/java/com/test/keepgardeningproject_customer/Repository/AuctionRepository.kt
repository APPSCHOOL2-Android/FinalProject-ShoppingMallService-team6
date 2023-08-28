package com.test.keepgardeningproject_customer.Repository

import android.provider.ContactsContract.Data
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.test.keepgardeningproject_customer.DAO.AuctionInfo

class AuctionRepository {
    companion object{
        //경매인덱스 저장
        fun setAuctionIndex(auctionIdx:Long,callback1:(it: Task<Void>)->Unit){
            val database = FirebaseDatabase.getInstance()
            val datbaseRef = database.getReference("AuctionIdx")
            datbaseRef.get().addOnCompleteListener {
                it.result.ref.setValue(auctionIdx).addOnCompleteListener (callback1)
            }
        }
        //경매인덱스 가져옴
        fun getAuctionIdx(callback1: (it: Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionIdx")
            databaseRef.get().addOnCompleteListener(callback1)
        }

        //경매내역전체를 가져옴
        fun getAuctionAll(callback1: (it: Task<DataSnapshot>) -> Unit){
            var database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Auction")
            databaseRef.orderByChild("auctionIdx").get().addOnCompleteListener(callback1)
        }

        //경매상품 저장
        fun setAuctionProduct(auctionInfo:AuctionInfo,callback1: (it: Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Auction")
            databaseRef.push().setValue(auctionInfo).addOnCompleteListener (callback1)
        }

        // 상품인덱스를 이용해 관련된 경매내역 호출
        fun getAuctionByProductIdx(auctionProductIdx : Double, callback1: (it: Task<DataSnapshot>) -> Unit){
            var database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Auction")
            databaseRef.orderByChild("auctionAuctionProductIdx").equalTo(auctionProductIdx).get().addOnCompleteListener(callback1)
        }
    }


}


