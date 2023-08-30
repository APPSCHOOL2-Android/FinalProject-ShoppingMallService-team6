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


    var paymentList = MutableLiveData<MutableList<purchaseInfo>>()
    var deliveryList = MutableLiveData<MutableList<purchaseInfo>>()
    var itemImage = MutableLiveData<Bitmap>()

    init {
        paymentList.value = mutableListOf<purchaseInfo>()
        deliveryList.value = mutableListOf<purchaseInfo>()
    }

    //postDataList 초기화
    fun resetList() {
        paymentList.value = mutableListOf<purchaseInfo>()
        deliveryList.value = mutableListOf<purchaseInfo>()
    }


    fun getData() {
        val templist = mutableListOf<purchaseInfo>()
        val templist2 = mutableListOf<purchaseInfo>()

        var useridx = MainActivity.loginedUserInfo.userIdx!!

        OrderProductRepository.getIndexorderInfo(useridx) {
            for (c1 in it.result.children) {
                var newstate = c1.child("ordersDeliveryState").value.toString()
                var newordersidx = c1.child("ordersIdx").value as Long
                var newproductidx = c1.child("ordersProductIdx").value as Long
                var newtotalorderidx = c1.child("ordersTotalOrderIdx").value as Long
                Log.d("lim useridx", "${newstate}")
                Log.d("lim ordersidx", "${newordersidx}")
                Log.d("lim productidx", "${newproductidx}")
                Log.d("lim totalorderidx", "${newtotalorderidx}")

                ProductRepository.getProductInfoByIdx(newproductidx.toDouble()) {
                    for (c2 in it.result.children) {
                        //결제한 상품의 이름,이미지,상태
                        var newname = c2.child("productName").value.toString()
                        var imglist = c2.child("productImageList").value as ArrayList<String>
                        var newimg = imglist[0]

                        Log.d("lim newname", "${newname}")
                        Log.d("lim newimg", "${newimg}")

                        if (newstate == "결제완료") {
                            var newclass = purchaseInfo(
                                newproductidx, newordersidx, useridx, newtotalorderidx,
                                newname, newimg, newstate
                            )
                            templist.add(newclass)
                            paymentList.value = templist
                            Log.d("lim paymentlist", "${paymentList.value}")
                        }
                        if (newstate == "배송완료") {
                            var newclass2 = purchaseInfo(
                                newproductidx, newordersidx, useridx, newtotalorderidx,
                                newname, newimg, newstate
                            )
                            templist2.add(newclass2)
                            deliveryList.value = templist2
                            Log.d("lim deliverylist", "${deliveryList.value}")
                        }


                    }
                }
            }

        }


    }


}