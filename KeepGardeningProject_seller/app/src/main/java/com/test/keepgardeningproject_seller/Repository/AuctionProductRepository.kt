package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.test.keepgardeningproject_seller.DAO.AuctionProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass

class AuctionProductRepository {

    companion object {

        // 경매 상품 인덱스 가져오기
        fun getAuctionProductIdx(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val auctionProductIdxRef = database.getReference("AuctionProductIdx")
            auctionProductIdxRef.get().addOnCompleteListener(callback1)
        }

        // 경매 상품 인덱스 저장
        fun setAuctionProductIdx(auctionProductIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("AuctionProductIdx")

            auctionProductIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(auctionProductIdx).addOnCompleteListener(callback1)
            }
        }

        // 경매 상품 정보 저장
        fun addAuctionProductInfo(auctionProductDataClass: AuctionProductClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("AuctionProduct")
            auctionProductIdxRef.push().setValue(auctionProductDataClass).addOnCompleteListener(callback1)
        }

        // 경매 상품 이미지 업로드
        fun uploadImage(
            uploadUri: Uri,
            fileName: String,
            callback1: (Task<UploadTask.TaskSnapshot>) -> Unit
        ) {
            val storage = FirebaseStorage.getInstance()
            val imageRef = storage.reference.child(fileName)
            imageRef.putFile(uploadUri).addOnCompleteListener(callback1)
        }

        // 해당 인덱스 경매 상품 정보 가져오기
        fun getAuctionProductInfoByIdx(auctionProductIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("AuctionProduct")
            auctionProductIdxRef.orderByChild("auctionProductIdx").equalTo(auctionProductIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 해당 스토어의 전체 경매 상품 정보 가져오기
        fun getAuctionProductInfoAll(storeIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auctionProductIdxRef = database.getReference("AuctionProduct")
            auctionProductIdxRef.orderByChild("auctionProductStoreIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }


        // 경매 상품 이미지 가져오기
        fun getAuctionProductImage(fileName : String, callback1:(Task<Uri>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)

            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        // 경매 상품 정보(글) 수정
        fun modifyAuctionProduct(auctionProductDataClass: AuctionProductClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("AuctionProduct")

            productDataRef.orderByChild("auctionProductIdx").equalTo(auctionProductDataClass.auctionProductIdx.toDouble()).get().addOnCompleteListener {
                for(a1 in it.result.children) {
                    a1.ref.child("auctionProductImageList").setValue(auctionProductDataClass.auctionProductImageList)
                    a1.ref.child("auctionProductName").setValue(auctionProductDataClass.auctionProductName)
                    a1.ref.child("auctionProductDetail").setValue(auctionProductDataClass.auctionProductDetail).addOnCompleteListener(callback1)
                }
            }
        }
    }
}