package com.test.keepgardeningproject_customer.UI.CartCustomer


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.Repository.CartRepository

class CartCustomerViewModel : ViewModel() {
    var cartCount = MutableLiveData<Int>()
    var productPrice = MutableLiveData<String>()

    var cartList = MutableLiveData<MutableList<CartClass>>()
    var cartImageList = MutableLiveData<MutableList<String>>()

    init {
        cartList.value = mutableListOf<CartClass>()
        cartImageList.value = mutableListOf<String>()
    }

    // 장바구니에 있는 상품의 정보 불러오기
    fun getProductInCart(cartUserIdx: Long) {
        val tempList = mutableListOf<CartClass>()
        val tempImageList = mutableListOf<String>()

        CartRepository.getCartbyUserIdx(cartUserIdx) {
            for (c1 in it.result.children) {
                val cartIdx = c1.child("cartIdx").value as Long
                val cartImage = c1.child("cartImage").value as String
                val cartName = c1.child("cartName").value as String
                val cartPrice = c1.child("cartPrice").value as Long
                val cartProductIdx = c1.child("cartProductIdx").value as Long
                val cartUserIdx = c1.child("cartUserIdx").value as Long
                val cartCount = c1.child("cartCount").value as Long

                val cart = CartClass(cartIdx, cartUserIdx, cartProductIdx, cartName, cartPrice, cartCount, cartImage)
                tempList.add(cart)
                tempImageList.add(cartImage)
            }
            tempList.reverse()
            tempImageList.reverse()
            cartList.value = tempList
            cartImageList.value = tempImageList
            cartCount.value = cartList.value!!.size
        }
    }

    // 상품 갯수 + 하기
    fun plusCartProduct(cartClass: CartClass) {
        CartRepository.plusCartProduct(cartClass) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 갯수 - 하기
    fun minusCartProduct(cartClass: CartClass) {
        CartRepository.minusCartProduct(cartClass) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 하나 삭제하기
    fun removeCartProduct(cartClass: CartClass) {
        CartRepository.removeCartProduct(cartClass.cartIdx) {
            getProductInCart(cartClass.cartUserIdx)
        }
    }

    // 상품 전체 삭제하기
    fun deleteAllCart(userIdx: Long) {
        CartRepository.deleteAllCart(userIdx) {
            getProductInCart(userIdx)
        }
    }
}