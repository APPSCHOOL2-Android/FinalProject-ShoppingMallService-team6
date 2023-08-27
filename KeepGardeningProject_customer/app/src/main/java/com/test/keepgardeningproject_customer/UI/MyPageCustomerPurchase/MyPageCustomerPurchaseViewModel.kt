package com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase

import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.purchaseInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository

class MyPageCustomerPurchaseViewModel : ViewModel() {


    var paymentList =MutableLiveData<MutableList<purchaseInfo>>()
    var deliveryList= MutableLiveData<MutableList<purchaseInfo>>()
    var itemImage = MutableLiveData<Bitmap>()

    init {
       paymentList.value = mutableListOf<purchaseInfo>()
        deliveryList.value = mutableListOf<purchaseInfo>()
    }


    fun getPaymentAll(){
        val templist = mutableListOf<purchaseInfo>()
        PurchaseRepository.getPurchaseAll {
            for(c1 in it.result.children){
                val purchaseidx = c1.child("purchaseIdx").value as Long
                val purchasename = c1.child("purchaseTitle").value as String
                val purchaseimg = c1.child("purchaseImg").value as String
                val purchasestate = c1.child("purchaseState").value as String
                var newclass = purchaseInfo(purchaseidx,purchasename, purchaseimg,purchasestate)
                templist.add(newclass)
            }
            paymentList.value = templist
        }
    }
    fun getDeliveryAll(){
        val templist2 = mutableListOf<purchaseInfo>()
        PurchaseRepository.getDeliveryPurchaseAll {
            for(c1 in it.result.children){
                val purchaseidx = c1.child("purchaseIdx").value as Long
                val purchasename = c1.child("purchaseTitle").value as String
                val purchaseimg = c1.child("purchaseImg").value as String
                val purchasestate = c1.child("purchaseState").value as String
                var newclass2 = purchaseInfo(purchaseidx,purchasename, purchaseimg,purchasestate)
                templist2.add(newclass2)
            }
            deliveryList.value = templist2
        }
    }

    //postDataList 초기화
    fun resetList(){
       paymentList.value = mutableListOf<purchaseInfo>()
       deliveryList.value = mutableListOf<purchaseInfo>()

    }

    //결제완료 데이터 추가
    fun getData() {
        var idx = MainActivity.loginedUserInfo.userIdx!!
        PurchaseRepository.getPurchaseIndex {
            var idx2 = it.result.value as Long
            idx2++
            //useridx로 결제한 상품을 가져옴
            OrderProductRepository.getIndexorderInfo(idx) {
                for (c1 in it.result.children) {
                    var newstate = c1.child("ordersDeliveryState").value.toString()
                    ProductRepository.getProductInfoByIdx(idx.toDouble()) {
                        for (c2 in it.result.children) {
                            //결제한 상품의 이름,이미지,상태
                            var newname = c2.child("productName").value.toString()
                            var imglist = c2.child("productImageList").value as ArrayList<String>
                            var newimg = imglist[0]
                            if(newstate== "결제완료"){
                                var newclass = purchaseInfo(idx,newname,newimg,newstate)
                                PurchaseRepository.setPurchaseInfo(newclass) {
                                    PurchaseRepository.setPurchaseIndex(idx2) {
                                        Log.d("Limidx","${newclass}")
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }


    }

    //베송완료 데이터 추가
    fun getData2() {
        var idx = MainActivity.loginedUserInfo.userIdx!!
        PurchaseRepository.getPurchaseDeliveryIndex {
            var idx2 = it.result.value as Long
            idx2++
            //useridx로 결제한 상품을 가져옴
            OrderProductRepository.getIndexorderInfo(idx) {
                for (c1 in it.result.children) {
                    var newstate = c1.child("ordersDeliveryState").value.toString()
                    ProductRepository.getProductInfoByIdx(idx.toDouble()) {
                        for (c2 in it.result.children) {
                            //결제한 상품의 이름,이미지,상태
                            var newname = c2.child("productName").value.toString()
                            var imglist = c2.child("productImageList").value as ArrayList<String>
                            var newimg = imglist[0]
                            if(newstate == "배송완료"){
                                var newclass = purchaseInfo(idx,newname,newimg,newstate)
                                PurchaseRepository.setDeliveryInfo(newclass) {
                                    PurchaseRepository.setDeliveryIndex(idx2) {
                                        Log.d("Lim","${newclass}")
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }


    }





}