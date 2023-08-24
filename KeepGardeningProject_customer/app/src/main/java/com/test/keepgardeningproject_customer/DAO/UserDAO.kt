package com.test.keepgardeningproject_customer.DAO

class UserDAO {


}

//사용자 정보 dataModel
data class UserInfo(
    var userIdx: Long?,
    var userLoginType: Long?,
    var userEmail: String?,
    var userPw: String?,
    var userNickname: String?
)


