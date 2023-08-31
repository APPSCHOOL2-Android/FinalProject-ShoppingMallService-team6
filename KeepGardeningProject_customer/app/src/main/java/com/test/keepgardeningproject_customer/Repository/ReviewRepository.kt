package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_customer.DAO.MypageReview
import com.test.keepgardeningproject_customer.DAO.Review

class ReviewRepository {
    companion object {
        // 리뷰 idx 가져오기
        fun getReviewIdx(callback1: (Task<DataSnapshot>) -> Unit) {

            val database = FirebaseDatabase.getInstance()
            val reviewIdxRef = database.getReference("ReviewIdx")

            reviewIdxRef.get().addOnCompleteListener(callback1)
        }

        // 리뷰 idx 저장하기
        fun setReviewIdx(reviewIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewIdxRef = database.getReference("ReviewIdx")

            reviewIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(reviewIdx).addOnCompleteListener(callback1)
            }
        }

        // 리뷰 데이터 저장하기
        fun addReviewInfo(review: Review, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")

            reviewDataRef.push().setValue(review).addOnCompleteListener(callback1)
        }

        // 리뷰 데이터 삭제하기
        fun removeReviewInfo(reviewIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")

            reviewDataRef.orderByChild("reviewIdx").equalTo(reviewIdx.toDouble()).get().addOnCompleteListener {
                for (a1 in it.result.children) {
                    // 해당 데이터를 삭제한다.
                    a1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }

        // 리뷰 idx를 통해 리뷰 데이터 얻어오기
        fun getReviewByIdx(reviewIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")

            reviewDataRef.orderByChild("reviewIdx").equalTo(reviewIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 상점 정보 얻어오기
        fun getStoreByStoreIdx(storeIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val storeRef = database.getReference("UserSellerInfo")

            storeRef.orderByChild("userSellerIdx").equalTo(storeIdx.toDouble()).get().addOnCompleteListener(callback1)
        }


        // 상품 정보 얻어오기
        fun getProductByProductIdx(productIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productRef = database.getReference("Product")

            productRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)
        }



        // 사용자 idx를 통해 리뷰 불러오기
        fun getReviewByUserIdx(userIdx: String, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewRef = database.getReference("Review")

            reviewRef.orderByChild("userIdx").equalTo(userIdx).get().addOnCompleteListener(callback1)
        }

        // 상품 이미지 불러오기
        fun getProductImage(fileName : String, callback1:(Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)

            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        fun getUserReview(userIdx: String, callback1: (Task<DataSnapshot>) -> Unit) {

            val database = FirebaseDatabase.getInstance()

            val reviewRef = database.getReference("Review").child(userIdx)

            reviewRef.get().addOnCompleteListener(callback1)


        }

        fun getReviewByProductIdx(productIdx : Long, callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val reviewRef = database.getReference("Review")
            reviewRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

    }

}