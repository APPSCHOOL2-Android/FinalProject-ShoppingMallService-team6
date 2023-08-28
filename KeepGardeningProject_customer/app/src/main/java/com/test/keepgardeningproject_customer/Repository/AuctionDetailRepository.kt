package com.test.keepgardeningproject_customer.Repository

import android.provider.ContactsContract.Data
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.test.keepgardeningproject_customer.DAO.AuctionDetailInfo

class AuctionDetailRepository {
}

// 경매내역 인덱스 저장
fun getAuctionDetailIndex(callback1 :(Task<DataSnapshot>)->Unit){
    var database = FirebaseDatabase.getInstance()
    var databaseRef = database.getReference("AuctionDetailIdx")
    databaseRef.get().addOnCompleteListener (callback1)
}

//경매내역 인덱스 가져옴
fun setAuctionDetailIndex(auctionDetailIdx:Long,callback1 :(Task<Void>)->Unit){
    var database = FirebaseDatabase.getInstance()
    var databaseRef = database.getReference("AuctionDetail")
    databaseRef.get().addOnCompleteListener {
        it.result.ref.push().setValue(auctionDetailIdx).addOnCompleteListener {
            callback1(it)
        }
    }
}

fun getAuctionDetailInfo(userIdx:Long){
    var database = FirebaseDatabase.getInstance()
    var databaseRef = database.getReference("AuctionDetail")


}

fun setAuctionDetailInfo(auctionDetailInfo: AuctionDetailInfo,callback1: (Task<Void>) -> Unit){
    var database = FirebaseDatabase.getInstance()
    var databaseRef = database.getReference("AuctionDetail")
    databaseRef.push().setValue(auctionDetailInfo).addOnCompleteListener (callback1)

}

