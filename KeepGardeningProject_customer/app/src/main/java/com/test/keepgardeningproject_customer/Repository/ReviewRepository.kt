package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.test.keepgardeningproject_customer.DAO.MypageReview
import com.test.keepgardeningproject_customer.DAO.Review

class ReviewRepository {

    companion object{

        //제품 이름 얻어오기

        fun getProductNameByProductIdx(productIdx:Long,callback1:(Task<DataSnapshot>)->Unit){

            val database = FirebaseDatabase.getInstance()

            val productRef = database.getReference("Product")

            productRef.orderByChild("productIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)

        }

        //상점 이름 얻어오기
        fun getStoreNameByStoreIdx(target_Idx: Long,callback1:(Task<DataSnapshot>)->Unit){

            val database = FirebaseDatabase.getInstance()

            val storeRef = database.getReference("UserSellerInfo")

            storeRef.orderByChild("userSellerIdx").equalTo(target_Idx.toDouble()).get().addOnCompleteListener(callback1)

        }


        //리뷰 데이터베이스에 올리기
        fun uploadReview(userIdx:String,review: Review){

            val database = FirebaseDatabase.getInstance()

            val reviewRef = database.getReference("Review")

            reviewRef.child(userIdx).push().setValue(review)

        }

        //Review->user ->review1,review2,review3....이다. review들을 얻어오는 함수이다.

        fun getReviewByuserIdx(userIdx:String,callback1: (Task<DataSnapshot>) -> Unit){

            val database = FirebaseDatabase.getInstance()

            val reviewRef = database.getReference("Review")

            reviewRef.orderByChild("userIdx").equalTo(userIdx).get().addOnCompleteListener(callback1)

        }

        fun getUserReview(userIdx:String,callback1: (Task<DataSnapshot>) -> Unit){

            val database = FirebaseDatabase.getInstance()

            val reviewRef = database.getReference("Review").child(userIdx)

            reviewRef.get().addOnCompleteListener(callback1)


        }



    }

}