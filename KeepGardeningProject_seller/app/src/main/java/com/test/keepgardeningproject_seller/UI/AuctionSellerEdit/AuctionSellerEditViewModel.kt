package com.test.keepgardeningproject_seller.UI.AuctionSellerEdit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.UI.AuctionSellerEdit.AuctionSellerEditFragment.Companion.originImageNum
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class AuctionSellerEditViewModel : ViewModel() {

    var auctionProductName = MutableLiveData<String>()
    var auctionProductDetail = MutableLiveData<String>()
    var auctionProductMainImage = MutableLiveData<Bitmap>()

    // 상품 이미지 이름 리스트
    var auctionProductImageNameList = MutableLiveData<MutableList<String>>()
    var auctionProductImageUriList = MutableLiveData<MutableList<Uri>>()

    init {
        auctionProductImageNameList.value = mutableListOf<String>()
        auctionProductImageUriList.value = mutableListOf<Uri>()
    }


    fun getAuctionProductInfo(auctionProductIdx: Long) {
        val tempImageNameList = mutableListOf<String>()
        val tempImageUriList = mutableListOf<Uri>()


        AuctionProductRepository.getAuctionProductInfoByIdx(auctionProductIdx) { it ->
            for (c1 in it.result.children) {

                var productImageList = c1.child("auctionProductImageList").value as ArrayList<String>
                auctionProductName.value = c1.child("auctionProductName").value as String
                auctionProductDetail.value = c1.child("auctionProductDetail").value as String

                for (i in 0 until productImageList.size) {
                    tempImageNameList.add(productImageList[i])
                }
            }


            auctionProductImageNameList.value = tempImageNameList

            AuctionProductRepository.getAuctionProductImage(auctionProductImageNameList.value!![0]) {
                thread {
                    // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                    val url = URL(it.result.toString())
                    // 접속한다.
                    val httpURLConnection = url.openConnection() as HttpURLConnection
                    // 이미지 객체를 생성한다.
                    val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)
                    auctionProductMainImage.postValue(bitmap)
                }
            }

            for(fileName in auctionProductImageNameList.value!!) {
                AuctionProductRepository.getAuctionProductImage(fileName) { uri ->
                    tempImageUriList.add(uri.result)
                    originImageNum = tempImageUriList.size
                }
            }

            auctionProductImageUriList.value = tempImageUriList
        }
    }
}