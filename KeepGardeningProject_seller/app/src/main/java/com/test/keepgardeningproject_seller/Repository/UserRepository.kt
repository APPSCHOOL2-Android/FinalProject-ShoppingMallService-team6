package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

import com.test.keepgardeningproject_seller.DAO.UserSellerInfo


class UserRepository {
    companion object{

        //사용자 로그인 정보 저장
        fun setUserSellerInfo(userSellerInfo: UserSellerInfo, callback1: (Task<Void>) -> Unit){

            val database = FirebaseDatabase.getInstance()
            val userSellerInfoData = database.getReference("UserSellerInfo")

            userSellerInfoData.push().setValue(userSellerInfo).addOnCompleteListener(callback1)

        }

        //사용자 인덱스 저장
        fun setUserSellerIdx(userSellerIdx:Long,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userSellerIdxRef = database.getReference("UserSellerIdx")

            userSellerIdxRef.get().addOnCompleteListener{
                it.result.ref.setValue(userSellerIdx).addOnCompleteListener {
                    callback1(it)
                }
            }
        }


        //사용자 인덱스 가져옴
        fun getUserSellerIndex(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userSellerIdxRef = database.getReference("UserSellerIdx")
            userSellerIdxRef.get().addOnCompleteListener(callback1)
        }

        //이메일로 통해 사용자 정보 가져옴
        fun getUserSellerInfoById(userSellerEmail:String,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userSellerDataRef = database.getReference("UserSellerInfo")
            userSellerDataRef.orderByChild("userSellerEmail").equalTo(userSellerEmail).get().addOnCompleteListener(callback1)
        }

        //사용자 정보 수정(마이페이지 수정)
        fun modifyUserSellerInfo(userInfo: UserSellerInfo,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userSellerInfoData = database.getReference("UserSellerInfo")
            userSellerInfoData.orderByChild("userSellerIdx").equalTo(userInfo.userSellerIdx.toDouble()).get().addOnCompleteListener {
                for(data in it.result.children){
                    data.ref.child("userSellerEmail").setValue(userInfo.userSellerEmail)
                    data.ref.child("userSellerPw").setValue(userInfo.userSellerPw)
                    data.ref.child("userSellerNickname").setValue(userInfo.userSellerNickname)
                    data.ref.child("userSellerBanner").setValue(userInfo.userSellerBanner)
                    data.ref.child("userSellerStoreName").setValue(userInfo.userSellerStoreName)
                    data.ref.child("userSellerStoreInfo").setValue(userInfo.userSellerStoreInfo)
                    data.ref.child("userSellerPostNumber").setValue(userInfo.userSellerPostNumber)
                    data.ref.child("userSellerPostDetail").setValue(userInfo.userSellerPostDetail)
                    data.ref.child("userSellerLoginType").setValue(userInfo.userSellerLoginType)
                }
            }
        }

        // 사용자 정보 삭제
        fun deleteUserSellerInfo(userInfoIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val userSellerInfoDataRef = database.getReference("UserSellerInfo")

            userSellerInfoDataRef.orderByChild("userSellerIdx").equalTo(userInfoIdx.toDouble()).get().addOnCompleteListener {
                for(c1 in it.result.children){
                    c1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }

        fun uploadImage(fileName:String,uploadUri: Uri,callback1: (Task<UploadTask.TaskSnapshot>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            var imageRef = storage.reference.child(fileName)
            imageRef.putFile(uploadUri!!).addOnCompleteListener(callback1)
        }

        // 상점 배너 이미지 업로드
        fun uploadStoreImage(uploadUri: Uri, fileName: String, callback1: (Task<UploadTask.TaskSnapshot>) -> Unit) {
            // firebase storage에 접근한다.
            val storage = FirebaseStorage.getInstance()
            // 파일에 접근할 수 있는 객체를 가져온다.
            var imageRef = storage.reference.child(fileName)

            // 파일을 업로드한다.
            // 안드로이드에서 파일을 선택하면 파일에 접근할 수 있는 uri 객체를 반환함
            // putFile 매개변수로 해당 uri 객체가 들어감
            imageRef.putFile(uploadUri!!).addOnCompleteListener(callback1)
        }

        //  상점 배너 이미지 가져오기
        fun getBannerImage(fileName : String, callback1:(Task<Uri>) -> Unit) {
            if(fileName != "None"){
                val storage = FirebaseStorage.getInstance()
                val fileRef = storage.reference.child(fileName)
                fileRef.downloadUrl.addOnCompleteListener(callback1)
            }


        }

        fun getUserNameByIdx(idx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("UserInfo")

            databaseRef.orderByChild("userIdx").equalTo(idx.toDouble()).get().addOnCompleteListener(callback1)
        }
    }
}