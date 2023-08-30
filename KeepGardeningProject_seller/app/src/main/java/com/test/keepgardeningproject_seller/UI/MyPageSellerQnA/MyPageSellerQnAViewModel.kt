package com.test.keepgardeningproject_seller.UI.MyPageSellerQnA

import android.os.Build.VERSION_CODES.P
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.qnaInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.Repository.QnARepository
import com.test.keepgardeningproject_seller.Repository.UserRepository

class MyPageSellerQnAViewModel : ViewModel() {


    var qnalist = MutableLiveData<MutableList<qnaInfo>>()

    init {
        qnalist.value = mutableListOf<qnaInfo>()
    }

    fun resetList(){
        qnalist.value = mutableListOf<qnaInfo>()
    }

    fun getData(idx:Long){
        var templist = mutableListOf<qnaInfo>()
        QnARepository.getQnAInfoByidx(idx){
            for(c1 in it.result.children){
                var qnaAnswer = c1.child("qnAAnswer").value as String
                var qnaTitle = c1.child("qnATitle").value as String
                var qnaIdx = c1.child("qnAIdx").value as Long
                var qnaproductIdx = c1.child("qnAProductIdx").value as Long
                var qnaCustomerIdx = c1.child("qnACustomerIdx").value as Long

                ProductRepository.getProductInfoByIdx(qnaproductIdx){
                    for(c3 in it.result.children){
                        var productName = c3.child("productName").value as String
                        var imglist = c3.child("productImageList").value as ArrayList<String>
                        var newimg = imglist[0]
                        var productidx= c3.child("productIdx").value as Long
                        UserRepository.getUserNameByIdx(qnaCustomerIdx){
                            for(b1 in it.result.children){
                                var name = b1.child("userNickname").value as String
                               
                                if(qnaAnswer=="None"){
                                    var  newAnswer = "미답변"
                                    var newclass = qnaInfo(qnaIdx,newAnswer,newimg,name,productName,qnaTitle,productidx)
                                    templist.add(newclass)
                                    qnalist.value = templist
                                }else{
                                    var newAnswer2 = "답변완료"
                                    var newclass2 = qnaInfo(qnaIdx,newAnswer2,newimg,name,productName,qnaTitle,productidx)
                                    templist.add(newclass2)
                                    qnalist.value = templist

                                }
                            }
                        }


                    }
                }





            }
        }
    }


}