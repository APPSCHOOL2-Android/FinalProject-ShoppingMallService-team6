package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.DAO.UserSellerInfo
import com.test.keepgardeningproject_customer.Repository.ProductRepository

class ProductCustomerDetailViewModel : ViewModel() {
    val productInfo = MutableLiveData<ProductClass>()
    val userSellerInfo = MutableLiveData<UserSellerInfo>()

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
}