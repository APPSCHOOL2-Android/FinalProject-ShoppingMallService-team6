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

        //사용자 정보 가져옴
        fun getUserSellerInfoByIndex(userSellerIdx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val userSellerDataRef = database.getReference("UserSellerInfo")
            userSellerDataRef.orderByChild("UserSellerIdx").equalTo(userSellerIdx.toDouble()).get().addOnCompleteListener(callback1)
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
            userSellerInfoData.orderByChild("UserSellerIdx").equalTo(userInfo.userSellerIdx.toDouble()).get().addOnCompleteListener {
                for(data in it.result.children){
                    data.ref.child("userSellerEmail").setValue(userInfo.userSellerEmail)
                    data.ref.child("userSellerPw").setValue(userInfo.userSellerPw)
                    data.ref.child("userSellerNickname").setValue(userInfo.userSellerNickname)
                    data.ref.child("userSellerBanner").setValue(userInfo.userSellerBanner)
                    data.ref.child("userSellerStoreName").setValue(userInfo.userSellerStoreName)
                    data.ref.child("userSellerStoreInfo").setValue(userInfo.userSellerStoreInfo)
                    data.ref.child("userSellerPostNumber").setValue(userInfo.userSellerPostNumber)
                    data.ref.child("userSellerPostDetail").setValue(userInfo.userSellerPostDetail)
                    data.ref.child("userSellerLoginType").setValue(userInfo.userSellerLoginType).addOnCompleteListener(callback1)
                }
            }
        }

        fun uploadImage(fileName:String,uploadUri: Uri,callback1: (Task<UploadTask.TaskSnapshot>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            val imageRef = storage.reference.child(fileName)
            imageRef.putFile(uploadUri!!).addOnCompleteListener(callback1)
        }

    }
}