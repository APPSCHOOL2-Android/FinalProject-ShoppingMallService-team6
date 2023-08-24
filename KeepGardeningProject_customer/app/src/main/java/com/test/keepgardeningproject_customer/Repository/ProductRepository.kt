package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_customer.DAO.ProductClass

class ProductRepository {
    companion object {

        // 상품 인덱스 가져오기
        fun getProductIdx(callback1:(Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val productIdxRef = database.getReference("ProductIdx")
            productIdxRef.get().addOnCompleteListener(callback1)
        }

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
            productDataRef.orderByChild("userSellerIdx").get().addOnCompleteListener(callback1)
        }

    }
}