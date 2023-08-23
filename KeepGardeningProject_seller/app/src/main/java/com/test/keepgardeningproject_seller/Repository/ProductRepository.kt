package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.test.keepgardeningproject_seller.DAO.ProductClass

class ProductRepository {

    companion object {

        // 상품 인덱스 가져오기
        fun getProductIdx(callback1:(Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val productIdxRef = database.getReference("ProductIdx")
            productIdxRef.get().addOnCompleteListener(callback1)
        }

        // 상품 인덱스 저장
        fun setProductIdx(productIdx:Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productIdxRef = database.getReference("ProductIdx")

            productIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(productIdx).addOnCompleteListener(callback1)
            }
        }

        // 상품 정보 저장
        fun addProductInfo(productDataClass: ProductClass, callback1:(Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productIdxRef = database.getReference("Product")
            productIdxRef.push().setValue(productDataClass).addOnCompleteListener(callback1)
        }

        // 상품 이미지 업로드
        fun uploadImage(uploadUri: Uri, fileName:String, callback1:(Task<UploadTask.TaskSnapshot>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val imageRef = storage.reference.child(fileName)
            imageRef.putFile(uploadUri).addOnCompleteListener(callback1)
        }
    }
}