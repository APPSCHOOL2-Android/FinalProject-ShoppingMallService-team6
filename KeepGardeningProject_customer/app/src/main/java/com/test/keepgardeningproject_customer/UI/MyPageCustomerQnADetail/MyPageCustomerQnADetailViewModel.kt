package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail


import android.net.Uri
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_customer.Repository.QnARepository
import kotlin.concurrent.thread

class MyPageCustomerQnADetailViewModel : ViewModel() {

    private val handler = Handler()

    var qnaTitle = MutableLiveData<String>()
    var qnaContent = MutableLiveData<String>()
    var qnaAnswer = MutableLiveData<String>()
    var qnaDate = MutableLiveData<String>()

    // 경매 상품 이미지 이름 리스트
    var qnaImageNameList = MutableLiveData<MutableList<String>>()
    var qnaImageUriList = MutableLiveData<MutableList<Uri>>()


    init {
        qnaImageNameList.value = mutableListOf<String>()
        qnaImageUriList.value = mutableListOf<Uri>()
    }


    fun getQnAInfo(qnaIdx: Long) {
        val tempImageNameList = mutableListOf<String>()
        val tempImageUriList = mutableListOf<Uri>()

        QnARepository.getQnAInfoByIdx(qnaIdx) {
            for (c1 in it.result.children) {
                var qnaImageList =
                    c1.child("qnAImageList").value as ArrayList<String>
                qnaTitle.value = c1.child("qnATitle").value as String
                qnaContent.value = c1.child("qnAContent").value as String
                qnaAnswer.value = c1.child("qnAAnswer").value as String
                qnaDate.value = c1.child("qnADate").value as String


                for (i in 0 until qnaImageList.size) {
                    tempImageNameList.add(qnaImageList[i])
                    Log.d("lion","name list for문 : ${tempImageNameList}")
                }
            }

//            // 가장 마지막에 등록한것부터 보여주기
//            tempImageNameList.reverse()

            qnaImageNameList.value = tempImageNameList
            Log.d("lion","name list : ${qnaImageNameList.value}")


//            for(fileName in qnaImageNameList.value!!) {
//                QnARepository.getQnAImage(fileName) { uri ->
//
//                    qnaImageUriList.value!!.add(uri.result)
//                    Log.d("lion", "get : ${qnaImageUriList.value}")
//
//                }
//            }

        }
    }

}