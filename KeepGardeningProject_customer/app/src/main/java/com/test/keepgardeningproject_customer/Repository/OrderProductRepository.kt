package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.test.keepgardeningproject_customer.DAO.OrdersProductClass

class OrderProductRepository {
    companion object {
        //userindex와 ordercustomerindex를 비교해서 데이터 가져오기
        fun getIndexorderInfo(useridx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("OrdersProduct")
            databaseRef.orderByChild("ordersCustomerIdx").equalTo(useridx.toDouble()).get().addOnCompleteListener(callback1)
        }    
        
        // userIdx를 통해 장바구니에 있는 상품 정보를 가져온다.
        fun getCartbyUserIdx(cartUserIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")
            cartDataRef.orderByChild("cartUserIdx").equalTo(cartUserIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 개별 주문 인덱스 번호를 가져온다.
        fun getOrdersProductIdx(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // 개별 주문 인덱스 번호
            val ordersIdxRef = database.getReference("OrdersIdx")
            ordersIdxRef.get().addOnCompleteListener(callback1)
        }

        // 개별 주문 인덱스 번호를 저장한다.
        fun setOrdersProductIdx(ordersIdx: Long) {
            val database = FirebaseDatabase.getInstance()
            // 개별 주문 인덱스 번호
            val ordersIdxRef = database.getReference("OrdersIdx")
            // 개별 주문 인덱스 번호 저장
            ordersIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(ordersIdx)
            }
        }

        // 개별 주문 정보를 저장한다.
        fun addOrdersProductInfo(ordersProductClass: OrdersProductClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            // PostData 데이터에 접근
            val ordersProductDataRef = database.getReference("OrdersProduct")
            // 개별 주문 정보 업로드
            ordersProductDataRef.push().setValue(ordersProductClass).addOnCompleteListener(callback1)
        }

        // 전체 주문 정보를 가져온다.
        fun getOrdersProductAll(callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val ordersProductRef = database.getReference("OrdersProduct")
            ordersProductRef.orderByChild("ordersIdx").get().addOnCompleteListener(callback1)
        }
    }
}

