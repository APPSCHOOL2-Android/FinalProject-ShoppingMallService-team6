package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_customer.DAO.Alert

class AlertRepository {


    companion object {
        // 현재 알림 인덱스를 가져온다. 
        fun getAlertIdx(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val AlertIdxRef = database.getReference("AlertIdx")
            AlertIdxRef.get().addOnCompleteListener(callback1)
        }

        // 장바구니 인덱스를 저장한다.
        fun setAlertIdx(AlertIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val AlertIdxRef = database.getReference("AlertIdx")
            AlertIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(AlertIdx).addOnCompleteListener(callback1)
            }
        }

        // 장바구니에 상품을 추가한다.
        fun addAlert(AlertClass: Alert, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val AlertDataRef = database.getReference("Alert")
            AlertDataRef.push().setValue(AlertClass).addOnCompleteListener(callback1)
        }




        // 상품 하나 삭제
        fun removeAlertProduct(AlertIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val AlertDataRef = database.getReference("Alert")

            AlertDataRef.orderByChild("AlertIdx").equalTo(AlertIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }

        // 상품 전체 삭제
        fun deleteAllAlert(userIdx: Long) {
            val database = FirebaseDatabase.getInstance()
            val AlertDataRef = database.getReference("Alert")

            AlertDataRef.orderByChild("AlertUserIdx").equalTo(userIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.removeValue()
                }
            }
        }
    }
}