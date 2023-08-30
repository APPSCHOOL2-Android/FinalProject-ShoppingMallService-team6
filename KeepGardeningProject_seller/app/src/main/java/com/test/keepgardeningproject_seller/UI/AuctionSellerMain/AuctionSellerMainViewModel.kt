package com.test.keepgardeningproject_seller.UI.AuctionSellerMain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.AuctionClass
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.AuctionRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class AuctionSellerMainViewModel : ViewModel() {

    var auctionProductName = MutableLiveData<String>()
    var auctionProductOpenPrice = MutableLiveData<String>()
    var auctionCurrentPrice = MutableLiveData<String>()
    var auctionProductOpenDate = MutableLiveData<String>()
    var auctionProductCloseDate = MutableLiveData<String>()
    var auctionProductDetail = MutableLiveData<String>()
    var auctionProductMainImage = MutableLiveData<Bitmap>()

    // 경매 상품 이미지 이름 리스트
    var auctionProductImageNameList = MutableLiveData<MutableList<String>>()


    init {
        auctionProductImageNameList.value = mutableListOf<String>()
    }


    fun getAuctionProductInfo(auctionProductIdx: Long) {
        val tempImageNameList = mutableListOf<String>()
        val tempPriceList = mutableListOf<String>()

        AuctionProductRepository.getAuctionProductInfoByIdx(auctionProductIdx) {
            for (c1 in it.result.children) {
                var productImageList = c1.child("auctionProductImageList").value as ArrayList<String>
                auctionProductName.value = c1.child("auctionProductName").value as String
                auctionProductOpenPrice.value = c1.child("auctionProductOpenPrice").value as String
                auctionProductOpenDate.value = c1.child("auctionProductOpenDate").value as String
                auctionProductCloseDate.value = c1.child("auctionProductCloseDate").value as String
                auctionProductDetail.value = c1.child("auctionProductDetail").value as String

                for (i in 0 until productImageList.size) {
                    tempImageNameList.add(productImageList[i])
                }
            }

//            // 가장 마지막에 등록한것부터 보여주기
//            tempImageNameList.reverse()

            auctionProductImageNameList.value = tempImageNameList


            AuctionRepository.getAuctionProductInfoAll(auctionProductIdx) {
                for (c1 in it.result.children) {
                    var auctionBidPrice = c1.child("auctionBidPrice").value as String

                    tempPriceList.add(auctionBidPrice)
                }

                // 가장 마지막에 등록한것부터 보여주기
                tempPriceList.reverse()

                auctionCurrentPrice.value = tempPriceList[0]
            }

            ProductRepository.getProductImage(auctionProductImageNameList.value!![0]) {
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
        }
    }
}