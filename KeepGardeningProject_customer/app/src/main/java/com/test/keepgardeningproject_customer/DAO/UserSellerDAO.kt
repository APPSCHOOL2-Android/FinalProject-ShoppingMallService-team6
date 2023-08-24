package com.test.keepgardeningproject_customer.DAO

class UserSellerDAO {
}

// 판매자 상점정보
data class UserSellerInfo(
    var userSellerIdx: Long?,
    var userSellerLoginType: Long?,
    var userSellerEmail: String?,
    val userSellerPw: String?,
    var userSellerNickname: String?,
    var userSellerBanner: String? = null,
    var userSellerStoreName: String?,
    var userSellerStoreInfo: String?,
    var userSellerPostNumber: String?,
    var userSellerPostDetail: String?
)