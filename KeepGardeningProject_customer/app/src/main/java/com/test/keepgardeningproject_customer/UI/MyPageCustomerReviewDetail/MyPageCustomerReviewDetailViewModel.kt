package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewDetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MyPageCustomerReviewDetailViewModel : ViewModel() {
    var reviewProductFileName = MutableLiveData<String>()
    var reviewProductImage = MutableLiveData<Bitmap>()
    var reviewProductName = MutableLiveData<String>()
    var reviewStoreName = MutableLiveData<String>()
    var reviewRating = MutableLiveData<Long>()
    var reviewTitle = MutableLiveData<String>()
    var reviewContent = MutableLiveData<String>()

    fun reset() {
        reviewProductFileName = MutableLiveData<String>()
        reviewProductImage = MutableLiveData<Bitmap>()
        reviewProductName = MutableLiveData<String>()
        reviewStoreName = MutableLiveData<String>()
        reviewRating = MutableLiveData<Long>()
        reviewTitle = MutableLiveData<String>()
        reviewContent = MutableLiveData<String>()
    }

    // 리뷰 상세내용 불러오기
    fun getReviewDetail(reviewIdx: Long) {
        ReviewRepository.getReviewByIdx(reviewIdx) {
            for (c1 in it.result.children) {
                reviewProductName.value = c1.child("productName").value as String
                reviewStoreName.value = c1.child("storeName").value as String
                reviewRating.value = c1.child("rating").value as Long
                reviewTitle.value = c1.child("reviewTitle").value as String
                reviewContent.value = c1.child("reviewContent").value as String
                val productIdx = c1.child("productIdx").value as Long

                ReviewRepository.getProductByProductIdx(productIdx) {
                    for (p1 in it.result.children) {
                        val productImageList = p1.child("productImageList").value as ArrayList<String>
                        reviewProductFileName.value = productImageList[0]

                        ReviewRepository.getProductImage(reviewProductFileName.value!!) {
                            thread {
                                // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                                val url = URL(it.result.toString())
                                // 접속한다.
                                val httpURLConnection = url.openConnection() as HttpURLConnection
                                // 이미지 객체를 생성한다.
                                val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)

                                // ----- 정리하기 -----
                                // 메인쓰레드가 아닌 곳에서 라이브데이터에 값을 할당하려면 postValue 사용
                                reviewProductImage.postValue(bitmap)
                            }
                        }
                    }
                }
            }
        }
    }
}