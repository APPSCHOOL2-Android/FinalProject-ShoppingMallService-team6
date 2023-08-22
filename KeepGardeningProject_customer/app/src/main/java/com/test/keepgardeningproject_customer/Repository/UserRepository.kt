package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.test.keepgardeningproject_customer.DAO.UserDAO
import com.test.keepgardeningproject_customer.DAO.UserInfo

class UserRepository {
    companion object{

        //로그인 ->홈

        //비회원가입시
        //카카오로 계속하기 -> 홈 -> 회원가입화면(닉네임) -> 마이페이지에서 수정(닉네임)
        //네이버로 계속하기  -> 홈
        //구글로 계속하기 -> 홈
        //이메일로 계속하기 -> 이메일 계속하기 화면  - 회원가입화면(이메일,비밀번호(확인),닉네임) -> 마이페이지 수정(비밀번호,닉네임)


        //회원가입시
        //카카오로 계속하기 -> 홈 -
        //네이버로 계속하기  -> 홈
        //구글로 계속하기 -> 홈
        //이메일로 계속하기 -> 홈


//        UserIndex:유저인덱스(Int)

//        UserLoginType: 로그인타입(0:일반, 1:카카오, 2:네이버, 3:구글)

//        UserEmail:이메일
//        UserPw:비밀번호
//        UserNickName:닉네임

//        UserStoreIndex?: 구매자인지 판매자인지

        //사용자 로그인 정보 저장
        fun setUserInfo(userInfo:UserInfo,callback1: (Task<Void>) -> Unit){

            val database = FirebaseDatabase.getInstance()
            val userInfoData = database.getReference("UserInfo")

            userInfoData.push().setValue(userInfo).addOnCompleteListener(callback1)

        }

        //사용자 인덱스 저장
        fun setUserIdx(userIdx:Long,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userIdxRef = database.getReference("UserIdx")

            userIdxRef.get().addOnCompleteListener{
                it.result.ref.setValue(userIdx).addOnCompleteListener {
                    callback1(it)
                }
            }
        }

        //사용자 인덱스 가져옴
        fun getUserIndex(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userIdxRef = database.getReference("UserIdx")
            userIdxRef.get().addOnCompleteListener(callback1)
        }

        //사용자 정보 가져옴
        fun getUserInfoByIndex(userIdx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userDataRef = database.getReference("UserInfo")
            userDataRef.orderByChild("UserIdx").equalTo(userIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        //이메일로 통해 사용자 정보 가져옴
        fun getUserInfoById(userEmail:String,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userDataRef = database.getReference("UserInfo")
            userDataRef.orderByChild("userEmail").equalTo(userEmail).get().addOnCompleteListener(callback1)
        }

        //사용자 정보 수정(마이페이지 수정)
        fun modifyUserInfo(userInfo: UserInfo,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userInfoData = database.getReference("UserInfo")
            userInfoData.orderByChild("UserIdx").equalTo(userInfo.userIdx.toDouble()).get().addOnCompleteListener {
                for(data in it.result.children){
                    data.ref.child("userEmail").setValue(userInfo.userEmail)
                    data.ref.child("userPw").setValue(userInfo.userPw)
                    data.ref.child("userNickname").setValue(userInfo.userNickname)
                    data.ref.child("userLoginType").setValue(userInfo.userLoginType).addOnCompleteListener(callback1)
                }
            }
        }





    }
}