package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnA

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.DAO.QnaInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.QnARepository
import com.test.keepgardeningproject_customer.Repository.StoreRepository

class MyPageCustomerQnAViewModel : ViewModel() {


    var qnalist = MutableLiveData<MutableList<QnaInfo>>()

    init {
        qnalist.value = mutableListOf<QnaInfo>()
    }
    fun resetList(){
        qnalist.value = mutableListOf<QnaInfo>()
    }


    fun getData(){
        var templist = mutableListOf<QnaInfo>()
        var useridx =  MainActivity.loginedUserInfo.userIdx!!

        QnARepository.getQnAByUserIdx(useridx){
            for(c1 in it.result.children){
                var qnaAnswer = c1.child("qnAAnswer").value as String


                var qnaContent = c1.child("qnAContent").value as String
                var qnaTitle = c1.child("qnATitle").value as String
                var qnaProductIdx = c1.child("qnAProductIdx").value as Long
                var qnaIdx = c1.child("qnAIdx").value as Long
                ProductRepository.getProductQnaInfoByIdx(qnaProductIdx){
                    for(c2 in it.result.children){
                        var imglist = c2.child("productImageList").value as ArrayList<String>
                        var qnanewimg = imglist[0]
                        var storeidx = c2.child("productStoreIdx").value as Long

                        StoreRepository.getSellerStorenameByIdx(storeidx){
                            for(c3 in it.result.children){
                                var qnastorename  = c3.child("userSellerStoreName").value

                                if(qnaAnswer == "None"){
                                    var newanswer = "미답변"
                                    var newclass = QnaInfo(qnaIdx,qnanewimg,qnastorename.toString(),qnaTitle,qnaContent,qnaProductIdx,storeidx,newanswer)
                                    templist.add(newclass)
                                    qnalist.value = templist

                                }else{
                                    var newanswer2 = "답변완료"
                                    var newclass = QnaInfo(qnaIdx,qnanewimg,qnastorename.toString(),qnaTitle,qnaContent,qnaProductIdx,storeidx,newanswer2)
                                    templist.add(newclass)
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