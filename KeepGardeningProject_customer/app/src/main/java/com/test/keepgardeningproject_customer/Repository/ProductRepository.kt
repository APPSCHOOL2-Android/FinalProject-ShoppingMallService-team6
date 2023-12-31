package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProductRepository {
    companion object {
        fun getProductInfoByIdx(productIdx:Double, callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productIdx").equalTo(productIdx).get().addOnCompleteListener(callback1)
        }

        fun getProductInfoAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productIdx").get().addOnCompleteListener(callback1)
        }

        fun getProductImage(fileName : String, callback1:(Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)

            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        fun getProductSellerInfoByIdx(storeIdx : Double, callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("UserSellerInfo")
            productDataRef.orderByChild("userSellerIdx").equalTo(storeIdx).get().addOnCompleteListener(callback1)
        }

        fun getProductQnaInfoByIdx(productidx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Product")
            databaseRef.orderByChild("productIdx").equalTo(productidx.toDouble()!!).get().addOnCompleteListener(callback1)

        }

    }
}