package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.test.keepgardeningproject_customer.DAO.MypageReview
import com.test.keepgardeningproject_customer.MainActivity

class ReviewRepository {

    companion object{

        //Review->userIdx->reviewList->
        fun setUserReview(userReview:MypageReview,callback1: (Task<Void>)->Unit){

            val database = FirebaseDatabase.getInstance()

            val setReview = database.getReference("Review")

            val userIdx = MainActivity.loginedUserInfo.userIdx

            setReview.child(userIdx.toString()).child("reviewList").push().setValue(userReview)

        }

        fun getUserReview(
            userIdx: String,
            callback1: (MutableList<MypageReview>) -> Unit
        ) {

            val reviewList: MutableList<MypageReview> = mutableListOf()

            val database = FirebaseDatabase.getInstance()
            val reviewRef = database.getReference("Review").child(userIdx).child("reviewList")

            reviewRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (reviewSnapshot in snapshot.children) {
                        val productName = reviewSnapshot.child("productName").getValue(String::class.java)
                        val ratings = reviewSnapshot.child("ratings").getValue(Double::class.java)
                        val reviewContent = reviewSnapshot.child("reviewContent").getValue(String::class.java)
                        val reviewImage = reviewSnapshot.child("reviewImage").getValue(Int::class.java)
                        val reviewTitle = reviewSnapshot.child("reviewTitle").getValue(String::class.java)
                        val storeName = reviewSnapshot.child("storeName").getValue(String::class.java)

                        if (productName != null && ratings != null && reviewContent != null &&
                            reviewImage != null && reviewTitle != null && storeName != null
                        ) {
                            val review = MypageReview(
                                 storeName, productName, ratings.toFloat(),
                                reviewImage, reviewTitle, reviewContent
                            )
                            reviewList.add(review)
                        }
                    }
                    callback1(reviewList)
                }
                override fun onCancelled(error: DatabaseError) {
                    // 오류 처리
                }
            })
        }
    }
}