package com.test.keepgardeningproject_customer.UI.MyPageCustomerReview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.ReviewRepository

class MyPageCustomerReviewViewModel : ViewModel() {
    var reviewList = MutableLiveData<MutableList<ReviewList>>()

    init {
        reviewList.value = mutableListOf<ReviewList>()
    }

    fun reset() {
        reviewList.value = mutableListOf<ReviewList>()
    }
    fun getReviewData(userIdx: String) {
        val tempList = mutableListOf<ReviewList>()
        val tempIdxList = mutableListOf<Long>()
        Log.i("s333", "userIdx:${userIdx}")

        // 사용자 idx를 통해 리뷰 가져오기
        ReviewRepository.getReviewByUserIdx(userIdx) {
            Log.i("s333", "get")
            for (c1 in it.result.children) {
                val storeName = c1.child("storeName").value as String
                val productName = c1.child("productName").value as String
                val reviewTitle = c1.child("reviewTitle").value as String
                val productIdx = c1.child("productIdx").value as Long
                val reviewRating = c1.child("rating").value as Long
                val reviewIdx = c1.child("reviewIdx").value as Long
                Log.i("s333", "productName : ${productName}")

                ReviewRepository.getProductByProductIdx(productIdx) {
                    for (p1 in it.result.children) {
                        val productImageList = p1.child("productImageList").value as ArrayList<String>
                        val productImage = productImageList[0]

                        val review = ReviewList(reviewIdx, productImage, storeName, productName, reviewTitle, reviewRating)
                        tempList.add(review)
                    }
                    tempList.reverse()
                    reviewList.value = tempList
                    Log.i("s333", reviewList.value.toString())
                }
            }
        }
    }
}

// 리뷰 목록 표시를 위한 클래스
data class ReviewList (
    val reviewIdx: Long,
    val productImage: String,
    val storeName: String,
    val productName: String,
    val reviewTitle: String,
    val reviewRating: Long
    )