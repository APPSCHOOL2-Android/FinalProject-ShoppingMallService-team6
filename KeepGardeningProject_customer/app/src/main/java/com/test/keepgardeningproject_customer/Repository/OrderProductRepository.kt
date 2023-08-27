package com.test.keepgardeningproject_customer.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class OrderProductRepository {

    companion object{

        //userindex와 ordercustomerindex를 비교해서 데이터 가져오기
        fun getIndexorderInfo(useridx:Long,callback1: (Task<DataSnapshot>) -> Unit){
            val database = FirebaseDatabase.getInstance()
            val databaseRef = database.getReference("OrdersProduct")
            databaseRef.orderByChild("ordersCustomerIdx").equalTo(useridx.toDouble()).get().addOnCompleteListener(callback1)
        }

        fun setOrderInfo(){

        }
    }
}

