package com.test.keepgardeningproject_customer.Repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.keepgardeningproject_customer.DAO.CartClass

class CartRepository {
    companion object {
        // 현재 장바구니 인덱스를 가져온다.
        fun getCartIdx(callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartIdxRef = database.getReference("CartIdx")
            cartIdxRef.get().addOnCompleteListener(callback1)
        }

        // 장바구니 인덱스를 저장한다.
        fun setCartIdx(cartIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartIdxRef = database.getReference("CartIdx")
            cartIdxRef.get().addOnCompleteListener {
                it.result.ref.setValue(cartIdx).addOnCompleteListener(callback1)
            }
        }

        // 장바구니에 상품을 추가한다.
        fun addCartInfo(cartClass: CartClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")

            cartDataRef.push().setValue(cartClass).addOnCompleteListener(callback1)
        }

        // userIdx를 통해 장바구니에 있는 상품 정보를 가져온다.
        fun getCartbyUserIdx(cartUserIdx: Long, callback1: (Task<DataSnapshot>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")
            cartDataRef.orderByChild("cartUserIdx").equalTo(cartUserIdx.toDouble()).get().addOnCompleteListener(callback1)
        }

        // 상품의 메인 이미지를 가져온다.
        fun getProductMainImage(fileName: String, callback1: (Task<Uri>) -> Unit) {
            val storage = FirebaseStorage.getInstance()
            val fileRef = storage.reference.child(fileName)
            // 데이터를 가져올 수 있는 경로를 가져온다.
            fileRef.downloadUrl.addOnCompleteListener(callback1)
        }

        // 상품 갯수 + 하기
        fun plusCartProduct(cartClass: CartClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")

            cartDataRef.orderByChild("cartIdx").equalTo(cartClass.cartIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.child("cartPrice").setValue(cartClass.cartPrice + (cartClass.cartPrice / cartClass.cartCount))
                    c1.ref.child("cartCount").setValue(cartClass.cartCount + 1).addOnCompleteListener(callback1)
                }
            }
        }

        // 상품 갯수 - 하기
        fun minusCartProduct(cartClass: CartClass, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")

            cartDataRef.orderByChild("cartIdx").equalTo(cartClass.cartIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.child("cartPrice").setValue(cartClass.cartPrice - (cartClass.cartPrice / cartClass.cartCount))
                    c1.ref.child("cartCount").setValue(cartClass.cartCount - 1).addOnCompleteListener(callback1)
                }
            }
        }

        // 상품 하나 삭제
        fun removeCartProduct(cartIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")

            cartDataRef.orderByChild("cartIdx").equalTo(cartIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }

        // 상품 전체 삭제
        fun deleteAllCart(userIdx: Long, callback1: (Task<Void>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val cartDataRef = database.getReference("Cart")

            cartDataRef.orderByChild("cartUserIdx").equalTo(userIdx.toDouble()).get().addOnCompleteListener {
                for (c1 in it.result.children) {
                    c1.ref.removeValue().addOnCompleteListener(callback1)
                }
            }
        }
    }
}