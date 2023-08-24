package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CartRepository {
    companion object {
        // userIdx를 통해 장바구니 정보를 가져온다.
        fun getCartData(cartUserIdx: Double, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")
            cartDataRef.orderByChild("cartUserIdx").equalTo(cartUserIdx).get().addOnCompleteListener(callback1)
        }

        // productIdx를 통해 장바구니에 담긴 상품 정보를 가져온다.
        fun getProductbyIdx(produtIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productIdx").equalTo(produtIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 전체 상품 정보를 가져온다.
        fun getProductAll(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val postDataRef = database.getReference("Product")
            postDataRef.orderByChild("productIdx").get().addOnCompleteListener(callback1)
        }


        // 상품의 메인 이미지를 가져온다.
        fun getProductMainImage(fileName: String, callback1: (Task<Uri>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)
            // 데이터를 가져올 수 있는 경로를 가져온다.
            fileRef.downloadUrl.addOnCompleteListener(callback1) }

        // 상품 삭제

        // 상품 전체 삭제
    }
}