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

        // 해당 인덱스 상품 정보 가져오기
        fun getProductInfoByIdx(productIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 해당 스토어의 전체 상품 정보 가져오기
        fun getProductInfoAll(storeIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productStoreIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 전체 상품 가져오기 overload 함수
        fun getProductInfoAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")
            productDataRef.orderByChild("productIdx").get().addOnCompleteListener(callback1)
        }

        // 상품 이미지 가져오기
        fun getProductImage(fileName : String, callback1:(Task<Uri>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)

            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        // 이미지 삭제
        fun removeImage(fileName:String, callback1:(Task<Void>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)
            // 파일을 삭제한다.
            fileRef.delete().addOnCompleteListener (callback1)
        }

        // 상품 정보(글) 삭제
        fun removeProduct(productIdx:Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val testDataRef = database.getReference("Product")

            testDataRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener {
                for(a1 in it.result.children) {
                    // 해당 데이터 삭제
                    a1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }

        // 상품 정보(글) 수정
        fun modifyProduct(productDataClass: ProductClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("Product")

            productDataRef.orderByChild("productIdx").equalTo(productDataClass.productIdx.toDouble()).get().addOnCompleteListener {
                for(a1 in it.result.children) {
                    a1.ref.child("productImageList").setValue(productDataClass.productImageList)
                    a1.ref.child("productName").setValue(productDataClass.productName)
                    a1.ref.child("productDetail").setValue(productDataClass.productDetail)
                    a1.ref.child("productCategory").setValue(productDataClass.productCategory)
                    a1.ref.child("productPrice").setValue(productDataClass.productPrice).addOnCompleteListener(callback1)
                }
            }
        }
    }
}