package com.test.keepgardeningproject_seller.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.test.keepgardeningproject_seller.DAO.OrdersProductClass

class OrderProductRepository {
    companion object {
        // 전체 주문 정보를 가져온다.
        fun getOrdersProductAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val ordersProductRef = database.getReference("OrdersProduct")
            ordersProductRef.orderByChild("ordersIdx").get().addOnCompleteListener(callback1)
        }

        // 개별 주문 정보를 수정한다.
        fun modifyOrdersProductInfo(ordersProductClass: OrdersProductClass, callback1: (Task<Void>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            // PostData 데이터에 접근
            val ordersProductDataRef = database.getReference("OrdersProduct")
            ordersProductDataRef.orderByChild("ordersIdx").equalTo(ordersProductClass.ordersIdx.toDouble()).get().addOnCompleteListener {
                for(a1 in it.result.children){
                    a1.ref.child("ordersDeliveryState").setValue(ordersProductClass.ordersDeliveryState)
                }
            }
        }
    }
}

