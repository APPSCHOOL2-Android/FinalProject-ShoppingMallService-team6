package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AuctionSellerDetailRepository {
    companion object{
        fun getAuctionSellerDetailAll(userIdx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("AuctionProduct")
            databaseRef.orderByChild("auctionProductStoreIdx").equalTo(userIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        fun getImage(filename:String,callback1: (Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            var storageRef = storage.reference.child(filename)
            storageRef.downloadUrl.addOnCompleteListener (callback1)
        }
    }
}


