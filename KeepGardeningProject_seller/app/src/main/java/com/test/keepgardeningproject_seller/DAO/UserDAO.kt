package com.test.keepgardeningproject_seller.DAO

class UserDAO {
}

//사용자 정보 dataModel
data class UserSellerInfo(
    var userSellerIdx: Long, var userSellerLoginType:Long,
    var userSellerEmail:String, val userSellerPw:String, var userSellerNickname: String,
    var userSellerBanner: String? = null,var userSellerStoreName:String,var userSellerStoreInfo:String,
    var userSellerPostNumber:String,var userSellerPostDetail:String)