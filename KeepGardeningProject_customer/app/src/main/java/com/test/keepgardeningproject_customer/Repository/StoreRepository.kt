package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import androidx.lifecycle.liveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class StoreRepository {
    companion object{
        // 모든 스토어 데이터 불러오기.
        fun getAllStoreInfo(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val storeDataRef = database.getReference("UserSellerInfo")

            storeDataRef.orderByChild("userSellerIdx").get().addOnCompleteListener(callback1)
        }

        fun getImage(fileName: String, callback1: (Task<Uri>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)
            // 데이터를 가져올 수 있는 경로를 가져온다.
            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        // 인덱스를 통해 스토어 데이터 불러오기.
        fun getStoreInfoByIdx(storeIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val storeDataRef = database.getReference("UserSellerInfo")

            storeDataRef.orderByChild("userSellerIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 인덱스를 통해 특정 스토어의 상품만 불러오기.
        fun getProductByStoreIdx(storeIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")

            productDataRef.orderByChild("productStoreIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 상품의 인덱스를 통해서 상품을 판매하는 스토어 불러오기
        fun getProductSellerInfoByIdx(storeIdx : Long, callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("UserSellerInfo")
            productDataRef.orderByChild("userSellerIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }
    }
}