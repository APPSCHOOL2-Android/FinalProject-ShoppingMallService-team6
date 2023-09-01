package com.test.keepgardeningproject_seller.UI.MyPageSellerReview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.model.User
import com.test.keepgardeningproject_seller.DAO.qnaInfo
import com.test.keepgardeningproject_seller.DAO.reviewInfo
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.Repository.ReviewRepository
import com.test.keepgardeningproject_seller.Repository.UserRepository

class MyPageSellerReviewViewModel : ViewModel() {

    var reviewlist = MutableLiveData<MutableList<reviewInfo>>()

    init {
        reviewlist.value = mutableListOf<reviewInfo>()
    }

    fun resetList(){
        reviewlist.value = mutableListOf<reviewInfo>()
    }


    fun getData(storeName: String){
        var templist = mutableListOf<reviewInfo>()
        ReviewRepository.getSellerReviewInfoByIdx(storeName){
            for(a1 in it.result.children){
                var reviewidx = a1.child("reviewIdx").value as Long
                var useridx = a1.child("userIdx").value as String
                var newuseridx = useridx.toLong()
                var productName = a1.child("productName").value as String
                var rating = a1.child("rating").value as Long
                var reviewTitle = a1.child("reviewTitle").value as String
                var productIdx = a1.child("productIdx").value as Long
                ProductRepository.getProductInfoByIdx(productIdx){
                    for(b1 in it.result.children){
                        var imglist = b1.child("productImageList").value as ArrayList<String>
                        var newimg = imglist[0]
                        UserRepository.getUserNameByIdx(newuseridx){
                            for(c1 in it.result.children){
                                var userName = c1.child("userNickname").value as String

                                var newclass = reviewInfo(reviewidx,newimg,userName,productName,rating,reviewTitle)
                                templist.add(newclass)
                                templist.reverse()
                                reviewlist.value = templist

                                Log.d("Lim reviewList","${reviewlist.value}")

                            }
                        }
                    }
                }
            }
        }
    }
}