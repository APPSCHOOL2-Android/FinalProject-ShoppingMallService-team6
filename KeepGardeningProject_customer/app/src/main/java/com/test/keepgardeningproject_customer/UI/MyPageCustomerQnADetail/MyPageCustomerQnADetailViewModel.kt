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

    var qnaTitle = MutableLiveData<String>()
    var qnaContent = MutableLiveData<String>()
    var qnaAnswer = MutableLiveData<String>()
    var qnaDate = MutableLiveData<String>()

    fun getQnAInfo(qnaIdx: Long) {

        QnARepository.getQnAInfoByIdx(qnaIdx) {
            for (c1 in it.result.children) {
                qnaTitle.value = c1.child("qnATitle").value as String
                qnaContent.value = c1.child("qnAContent").value as String
                qnaAnswer.value = c1.child("qnAAnswer").value as String
                qnaDate.value = c1.child("qnADate").value as String
            }
        }
    }
}