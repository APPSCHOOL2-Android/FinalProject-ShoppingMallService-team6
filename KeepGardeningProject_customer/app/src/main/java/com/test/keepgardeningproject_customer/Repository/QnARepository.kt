package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.test.keepgardeningproject_customer.DAO.QnAClass

class QnARepository {
    companion object {
        // 문의 인덱스 가져오기
        fun getQnAIdx(callback1:(Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val qnaIdxRef = database.getReference("QnAIdx")
            qnaIdxRef.get().addOnCompleteListener(callback1)
        }

        // 문의 인덱스 저장
        fun setQnAIdx(qnaIdx:Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val qnaIdxRef = database.getReference("QnAIdx")

            qnaIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(qnaIdx).addOnCompleteListener(callback1)
            }
        }

        // 문의 글 저장
        fun addQnAInfo(qnaDataClass: QnAClass, callback1:(Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val qnaIdxRef = database.getReference("QnA")
            qnaIdxRef.push().setValue(qnaDataClass).addOnCompleteListener(callback1)
        }

        // 문의 이미지 업로드
        fun uploadImage(uploadUri: Uri, fileName:String, callback1:(Task<UploadTask.TaskSnapshot>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val imageRef = storage.reference.child(fileName)
            imageRef.putFile(uploadUri).addOnCompleteListener(callback1)
        }
    }
}