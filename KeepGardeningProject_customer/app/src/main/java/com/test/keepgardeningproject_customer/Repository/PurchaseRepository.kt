package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage


class PurchaseRepository {
    companion object{
        fun getImage(filename:String,callback1: (Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            var storageRef = storage.reference.child(filename)
            storageRef.downloadUrl.addOnCompleteListener (callback1)
        }
    }
}

