package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MyPageCustomerReviewWriteViewModel : ViewModel() {
    var reviewProductFileName = MutableLiveData<String>()
    var reviewProductImage = MutableLiveData<Bitmap>()
    var reviewProductName = MutableLiveData<String>()
    var reviewStoreName = MutableLiveData<String>()

    // 리뷰를 작성하는 상품에 대한 정보를 불러온다.
    fun getProductInfo(productIdx: Long) {
        ReviewRepository.getProductByProductIdx(productIdx) {
            for (c1 in it.result.children) {
                reviewProductName.value = c1.child("productName").value as String
                val storeIdx = c1.child("productStoreIdx").value as Long
                val productImage = c1.child("productImageList").value as ArrayList<String>
                reviewProductFileName.value = productImage[0]

                ProductRepository.getProductImage(reviewProductFileName.value!!) {
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

                ReviewRepository.getStoreByStoreIdx(storeIdx) {
                    for (c1 in it.result.children) {
                        reviewStoreName.value = c1.child("userSellerStoreName").value as String
                    }
                }
            }
        }
    }

}