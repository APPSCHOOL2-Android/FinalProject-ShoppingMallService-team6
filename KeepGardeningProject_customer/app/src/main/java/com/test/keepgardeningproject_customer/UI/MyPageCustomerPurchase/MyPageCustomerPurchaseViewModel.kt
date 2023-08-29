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
        var myidx = MainActivity.loginedUserInfo.userIdx as Long
        val templist = mutableListOf<purchaseInfo>()
        PurchaseRepository.getPurchaseAll(myidx) {
            for(c1 in it.result.children){
                val orderproductidx = c1.child("orderproductIdx").value as Long
                val orderidx = c1.child("ordersIdx").value as Long
                val purchaseidx = c1.child("purchaseIdx").value as Long
                val purchaseimg = c1.child("purchaseImg").value as String
                val purchaseinfoidx = c1.child("purchaseInfoIdx").value as Long
                val purchasestate = c1.child("purchaseState").value as String
                val purchasename = c1.child("purchaseTitle").value as String
                val totalorderidx = c1.child("totalorderIdx").value as Long
                var newclass = purchaseInfo(orderproductidx,orderidx,purchaseidx,totalorderidx,purchaseinfoidx,
                purchasename,purchaseimg,purchasestate)
                templist.add(newclass)
            }
            paymentList.value = templist
        }
   }
    fun getDeliveryAll(){
        var myidx = MainActivity.loginedUserInfo.userIdx as Long
        val templist2 = mutableListOf<purchaseInfo>()
        PurchaseRepository.getDeliveryPurchaseAll(myidx) {
            for(c1 in it.result.children){
                val orderproductidx = c1.child("orderproductIdx").value as Long
                val orderidx = c1.child("ordersIdx").value as Long
                val purchaseidx = c1.child("purchaseIdx").value as Long
                val purchaseimg = c1.child("purchaseImg").value as String
                val purchaseinfoidx = c1.child("purchaseInfoIdx").value as Long
                val purchasestate = c1.child("purchaseState").value as String
                val purchasename = c1.child("purchaseTitle").value as String
                val totalorderidx = c1.child("totalorderIdx").value as Long
                var newclass2 = purchaseInfo(orderproductidx,orderidx,purchaseidx,totalorderidx,purchaseinfoidx,
                    purchasename,purchaseimg,purchasestate)
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
        var useridx = MainActivity.loginedUserInfo.userIdx!!
        PurchaseRepository.getPurchaseIndex {
            //idx2 == purchaseInfoIdx:Long
            var purchaseinfoidx = it.result.value as Long
            purchaseinfoidx++
            //useridx로 결제한 상품을 가져옴
            OrderProductRepository.getIndexorderInfo(useridx) {
                for (c1 in it.result.children) {
                    var newstate = c1.child("ordersDeliveryState").value.toString()
                    var newordersidx = c1.child("ordersIdx").value as Long
                    var newproductidx =  c1.child("ordersProductIdx").value as Long
                    var newtotalorderidx = c1.child("ordersTotalOrderIdx").value as Long
                    ProductRepository.getProductInfoByIdx(useridx.toDouble()) {
                        for (c2 in it.result.children) {
                            //결제한 상품의 이름,이미지,상태
                            var newname = c2.child("productName").value.toString()
                            var imglist = c2.child("productImageList").value as ArrayList<String>
                            var newimg = imglist[0]

                            if(newstate== "결제완료"){
                                var newclass = purchaseInfo(newproductidx,newordersidx,useridx,newtotalorderidx,purchaseinfoidx,
                                newname,newimg,newstate)
                                PurchaseRepository.setPurchaseInfo(newclass) {
                                    PurchaseRepository.setPurchaseIndex(purchaseinfoidx) {
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
        var useridx = MainActivity.loginedUserInfo.userIdx!!
        PurchaseRepository.getPurchaseDeliveryIndex {
            var purchaseinfoidx = it.result.value as Long
            purchaseinfoidx++
            //useridx로 결제한 상품을 가져옴
            OrderProductRepository.getIndexorderInfo(useridx) {
                for (c1 in it.result.children) {
                    var newstate = c1.child("ordersDeliveryState").value.toString()
                    var newordersidx = c1.child("ordersIdx").value as Long
                    var newproductidx =  c1.child("ordersProductIdx").value as Long
                    var newtotalorderidx = c1.child("ordersTotalOrderIdx").value as Long
                    ProductRepository.getProductInfoByIdx(useridx.toDouble()) {
                        for (c2 in it.result.children) {
                            //결제한 상품의 이름,이미지,상태
                            var newname = c2.child("productName").value.toString()
                            var imglist = c2.child("productImageList").value as ArrayList<String>
                            var newimg = imglist[0]
                            if(newstate == "배송완료"){
                                var newclass = purchaseInfo(newproductidx,newordersidx,useridx,newtotalorderidx,purchaseinfoidx,newname,newimg,newstate)
                                PurchaseRepository.setDeliveryInfo(newclass) {
                                    PurchaseRepository.setDeliveryIndex(purchaseinfoidx) {
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