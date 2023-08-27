package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_customer.DAO.purchaseInfo

class PurchaseRepository {

    companion object{
        //결제완료 정보 저장
        fun setPurchaseInfo(purchaseInfo: purchaseInfo,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Purchase")
            databaseRef.push().setValue(purchaseInfo).addOnCompleteListener (callback1)
        }
        //배송완료 정보 저장
        fun setDeliveryInfo(purchaseInfo: purchaseInfo,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("DeliveryPurchase")
            databaseRef.push().setValue(purchaseInfo).addOnCompleteListener (callback1)
        }

        //결제완료 인덱스 저장
        fun setPurchaseIndex(purchaseIdx:Long,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("PurchaseIdx")
            databaseRef.get().addOnCompleteListener {
                it.result.ref.setValue(purchaseIdx).addOnCompleteListener{
                    callback1(it)
                }
            }
        }
        //배송완료 인덱스 저장
        fun setDeliveryIndex(purchaseIdx:Long,callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("DeliveryPurchaseIdx")
            databaseRef.get().addOnCompleteListener {
                it.result.ref.setValue(purchaseIdx).addOnCompleteListener{
                    callback1(it)
                }
            }
        }

        //결제완료 인덱스 가져오기
        fun getPurchaseIndex(callback1 :(Task<DataSnapshot>)->Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("PurchaseIdx")
            databaseRef.get().addOnCompleteListener (callback1)
        }

        //배송완료 인덱스 가져오기
        fun getPurchaseDeliveryIndex(callback1 :(Task<DataSnapshot>)->Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("DeliveryPurchaseIdx")
            databaseRef.get().addOnCompleteListener (callback1)
        }

        //결제완료 모든 정보 가져오기
        fun getPurchaseAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database=  FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("Purchase")
            databaseRef.orderByChild("purchaseIdx").get().addOnCompleteListener(callback1)
        }

        // 배송완료 모든 정보 가져오기
        fun getDeliveryPurchaseAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database=  FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("DeliveryPurchase")
            databaseRef.orderByChild("deliveryPurchaseIdx").get().addOnCompleteListener(callback1)
        }

        fun getImage(filename:String,callback1: (Task<Uri>) -> Unit){
            val storage = FirebaseStorage.getInstance()
            var storageRef = storage.reference.child(filename)
            storageRef.downloadUrl.addOnCompleteListener (callback1)
        }



    }
}

