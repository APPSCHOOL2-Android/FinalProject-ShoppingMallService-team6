package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.AuctionProductInfo
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.DAO.UserSellerInfo
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository

class AuctionCustomerDetailViewModel : ViewModel() {
    val auctionProductInfo = MutableLiveData<AuctionProductInfo>()
    val userSellerInfo = MutableLiveData<UserSellerInfo>()

    fun getAPByIdx(idx : Double){
        AuctionProductRepository.getAuctionProductByIdx(idx){
            for (c1 in it.result.children) {
                val auctionProductIdx = c1.child("auctionProductIdx").value as Long
                var auctionProductName = c1.child("auctionProductName").value as String
                var auctionProductDetail = c1.child("auctionProductDetail").value as String
                var auctionProductStoreIdx = c1.child("auctionProductStoreIdx").value as Long

                var auctionProductOpenPrice = c1.child("auctionProductOpenPrice").value as String
                var auctionProductOpenDate = c1.child("auctionProductOpenDate").value as String
                var auctionProductCloseDate = c1.child("auctionProductCloseDate").value as String

                var auctionProductImageList =
                    c1.child("auctionProductImageList").value as ArrayList<String>

                val p1 = AuctionProductInfo(
                    auctionProductIdx,
                    auctionProductName,
                    auctionProductDetail,
                    auctionProductStoreIdx,
                    auctionProductOpenPrice,
                    auctionProductOpenDate,
                    auctionProductCloseDate,
                    auctionProductImageList
                )

                auctionProductInfo.value = p1
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