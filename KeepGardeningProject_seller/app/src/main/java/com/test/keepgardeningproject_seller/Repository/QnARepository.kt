package com.test.keepgardeningproject_seller.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.DAO.QnAClass

class QnARepository {
    companion object {
        // 문의 인덱스 가져오기
        fun getQnAIdx(callback1:(Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val qnaIdxRef = database.getReference("QnAIdx")
            qnaIdxRef.get().addOnCompleteListener(callback1)
        }

        // 해당 인덱스 문의 정보 가져오기
        fun getQnAInfoByIdx(qnaIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val qnaDataRef = database.getReference("QnA")
            qnaDataRef.orderByChild("qnAIdx").equalTo(qnaIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 해당 상품의 전체 문의 정보 가져오기
        fun getQnAInfoAllByProduct(productIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val productDataRef = database.getReference("QnA")
            productDataRef.orderByChild("qnAProductIdx").equalTo(productIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 문의 답변 수정
        fun modifyQnAAnswer(qnaDataClass: QnAClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val qnaDataRef = database.getReference("QnA")

            qnaDataRef.orderByChild("qnAIdx").equalTo(qnaDataClass.QnAIdx.toDouble()).get().addOnCompleteListener {
                for(a1 in it.result.children) {
                    a1.ref.child("qnAAnswer").setValue(qnaDataClass.QnAAnswer).addOnCompleteListener(callback1)
                }
            }
        }
    }
}