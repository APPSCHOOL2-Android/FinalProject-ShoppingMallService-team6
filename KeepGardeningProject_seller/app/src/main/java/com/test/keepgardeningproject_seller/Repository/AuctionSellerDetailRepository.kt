package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_seller.DAO.auctionInfo

class AuctionSellerDetailRepository {
    companion object{
        fun getAuctionSellerDetailIdx(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionSellerDetailIdx")
            databaseRef.get().addOnCompleteListener (callback1)
        }

        fun setAuctioSellerDetailIdx(auctiondetailIdx:Long,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionSellerDetailIdx")
            databaseRef.get().addOnCompleteListener {
                it.result.ref.setValue(auctiondetailIdx).addOnCompleteListener {
                    callback1(it)
                }
            }

        }

        fun getAuctionSellerDetailAll(userIdx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionSellerDetail")
            databaseRef.orderByChild("auctionDetailIdx").equalTo(userIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        fun setAuctionSellerDetailInfo(auctionInfo:auctionInfo,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionSellerDetail")
            databaseRef.push().setValue(auctionInfo).addOnCompleteListener (callback1)

        }

        fun getImage(filename:String,callback1: (Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            var storageRef = storage.reference.child(filename)
            storageRef.downloadUrl.addOnCompleteListener (callback1)
        }
    }
}


