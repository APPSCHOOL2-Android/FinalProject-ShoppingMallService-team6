package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.DAO.Review
import com.test.keepgardeningproject_customer.DAO.UserSellerInfo
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import com.test.keepgardeningproject_customer.Repository.UserRepository

class ProductCustomerDetailViewModel : ViewModel() {
    val productInfo = MutableLiveData<ProductClass>()
    val imageList = MutableLiveData<MutableList<String>>()
    val userSellerInfo = MutableLiveData<UserSellerInfo>()
    val reviewList = MutableLiveData<MutableList<Review>>()

    val userName = MutableLiveData<String>()

    init {
        imageList.value = mutableListOf<String>()
        reviewList.value = mutableListOf<Review>()
    }

    fun getProductInfoByIdx(idx: Double) {
        ProductRepository.getProductInfoByIdx(idx) {
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

                productInfo.value = productClass
                imageList.value = productImageList
            }
        }
    }

    fun getUserSellerInfoByStoreIdx(idx: Double) {
        ProductRepository.getProductSellerInfoByIdx(idx) {
            for (c1 in it.result.children) {
                var userSellerIdx = c1.child("userSellerIdx").value as Long?
                var userSellerLoginType = c1.child("userSellerLoginType").value as Long?
                var userSellerEmail = c1.child("userSellerEmail").value as String?
                val userSellerPw = c1.child("userSellerPw").value as String?
                var userSellerNickname = c1.child("userSellerNickname").value as String?
                var userSellerBanner = c1.child("userSellerBanner").value as String?
                var userSellerStoreName = c1.child("userSellerStoreName").value as String?
                var userSellerStoreInfo = c1.child("userSellerStoreInfo").value as String?
                var userSellerPostNumber = c1.child("userSellerPostNumber").value as String?
                var userSellerPostDetail = c1.child("userSellerPostDetail").value as String?

                val userSellerInfoTemp = UserSellerInfo(
                    userSellerIdx,
                    userSellerLoginType,
                    userSellerEmail,
                    userSellerPw,
                    userSellerNickname,
                    userSellerBanner,
                    userSellerStoreName,
                    userSellerStoreInfo,
                    userSellerPostNumber,
                    userSellerPostDetail
                )

                userSellerInfo.value = userSellerInfoTemp
            }
        }
    }

    fun getReviewByProduct(_productIdx: Long) {
        val tempList = mutableListOf<Review>()
        ReviewRepository.getReviewByProductIdx(_productIdx) {
            for (c1 in it.result.children) {
                val reviewIdx = c1.child("reviewIdx").value as Long
                val userIdx = c1.child("userIdx").value as String
                val productIdx = c1.child("productIdx").value as Long
                val productName = c1.child("productName").value as String
                val storeName = c1.child("storeName").value as String
                val rating = c1.child("rating").value as Long
                val reviewTitle = c1.child("reviewTitle").value as String
                val reviewContent = c1.child("reviewContent").value as String

                val p1 = Review(
                    reviewIdx,
                    userIdx,
                    productIdx,
                    productName,
                    storeName,
                    rating,
                    reviewTitle,
                    reviewContent
                )
                tempList.add(p1)
            }

            tempList.reverse()
            reviewList.value = tempList
        }
    }

    fun getName(_userIdx : Long){
        UserRepository.getUserInfoByUserIdx(_userIdx){
            for(c1 in it.result.children){
                val userNickname = c1.child("userNickname").value as String

                userName.value = userNickname
            }
        }
    }
}