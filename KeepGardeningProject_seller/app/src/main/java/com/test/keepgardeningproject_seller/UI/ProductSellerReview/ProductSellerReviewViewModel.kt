package com.test.keepgardeningproject_seller.UI.ProductSellerReview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.QnAClass
import com.test.keepgardeningproject_seller.DAO.ReviewClass
import com.test.keepgardeningproject_seller.Repository.QnARepository
import com.test.keepgardeningproject_seller.Repository.ReviewRepository

class ProductSellerReviewViewModel : ViewModel() {
    // 전체 리뷰 목록
    var reviewClassList = MutableLiveData<MutableList<ReviewClass>>()

    init {
        reviewClassList.value = mutableListOf<ReviewClass>()
    }


    val tempList = mutableListOf<ReviewClass>()
    fun getReviewInfoAll(productIdx: Long) {

        ReviewRepository.getReviewInfoAllByProduct(productIdx) {
            for (c1 in it.result.children) {
                val reviewIdx = c1.child("reviewIdx").value as Long
                val reviewUserIdx = c1.child("userIdx").value as String
                val reviewProductIdx = c1.child("productIdx").value as Long
                val reviewProductName = c1.child("productName").value as String
                val reviewStoreName = c1.child("storeName").value as String
                val reviewRating = c1.child("rating").value as Long
                val reviewTitle = c1.child("reviewTitle").value as String
                val reviewContent = c1.child("reviewContent").value as String

                val r1 = ReviewClass(
                    reviewIdx,
                    reviewUserIdx,
                    reviewProductIdx,
                    reviewProductName,
                    reviewStoreName,
                    reviewRating,
                    reviewTitle,
                    reviewContent
                )
                tempList.add(r1)

            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()

            reviewClassList.value = tempList

        }
    }
}