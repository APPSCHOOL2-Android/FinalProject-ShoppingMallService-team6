package com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.Repository.StoreRepository
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class StoreInfoCustomerDetailViewModel : ViewModel() {
    var storeImage = MutableLiveData<String>()
    var storeName = MutableLiveData<String>()
    var storeAddress = MutableLiveData<String>()
    var storeDetail = MutableLiveData<String>()
    val storeBitmap = MutableLiveData<Bitmap>()

    val productList = MutableLiveData<MutableList<ProductClass>>()
    val productImageList = MutableLiveData<MutableList<String>>()

    init {
        productList.value = mutableListOf<ProductClass>()
        productImageList.value = mutableListOf<String>()
    }

    // 인덱스를 통해 스토어 불러오기
    fun getStoreInfo(storeIdx: Long) {
        StoreRepository.getStoreInfoByIdx(storeIdx) {
            for (c1 in it.result.children) {
                storeImage.value = c1.child("userSellerBanner").value as String
                storeName.value = c1.child("userSellerStoreName").value as String
                storeAddress.value = "${c1.child("userSellerPostNumber").value as String} ${c1.child("userSellerPostDetail").value as String}"
                storeDetail.value = c1.child("userSellerStoreInfo").value as String

                if (storeImage.value != "None") {
                    Log.i("f222", storeImage.value!!)
                    StoreRepository.getImage(storeImage.value!!) {
                        thread {
                            // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                            val url = URL(it.result.toString())
                            // 접속한다.
                            val httpURLConnection = url.openConnection() as HttpURLConnection
                            // 이미지 객체를 생성한다.
                            val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)

                            // ----- 정리하기 -----
                            // 메인쓰레드가 아닌 곳에서 라이브데이터에 값을 할당하려면 postValue 사용
                            storeBitmap.postValue(bitmap)
                        }
                    }
                }
            }
        }
    }

    // 스토어의 상품을 불러오기
    fun getStoreProduct(storeIdx: Long) {
        val tempList = mutableListOf<ProductClass>()
        val tempImageList = mutableListOf<String>()

        StoreRepository.getProductByStoreIdx(storeIdx) {
            for (c1 in it.result.children) {
                val productIdx = c1.child("productIdx").value as Long
                var productImageList = c1.child("productImageList").value as ArrayList<String>
                var productName = c1.child("productName").value as String
                var productPrice = c1.child("productPrice").value as String
                var productStoreIdx = c1.child("productStoreIdx").value as Long
                var productCategory = c1.child("productCategory").value as String
                var productDetail = c1.child("productDetail").value as String

                val productClass = ProductClass(
                        productIdx,
                        productImageList,
                        productName,
                        productPrice,
                        productStoreIdx,
                        productCategory,
                        productDetail
                )

                tempList.add(productClass)
                tempImageList.add(productImageList[0])
            }

            tempList.reverse()
            tempImageList.reverse()

            productList.value = tempList
            productImageList.value = tempImageList
        }
    }
}