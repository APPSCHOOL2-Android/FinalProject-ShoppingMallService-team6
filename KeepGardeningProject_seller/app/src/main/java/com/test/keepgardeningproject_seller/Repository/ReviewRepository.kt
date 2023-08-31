package com.test.keepgardeningproject_seller.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class ReviewRepository {

    companion object {
        // 해당 인덱스 리뷰 정보 가져오기
        fun getReviewInfoByIdx(reviewIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")
            reviewDataRef.orderByChild("reviewIdx").equalTo(reviewIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 해당 상품의 전체 리뷰 정보 가져오기
        fun getReviewInfoAllByProduct(productIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")
            reviewDataRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        fun getSellerReviewInfoByIdx(storeName:String,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val reviewDataRef = database.getReference("Review")
            reviewDataRef.orderByChild("storeName").equalTo(storeName).get().addOnCompleteListener (callback1)

        }
    }
}
