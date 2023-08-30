package com.test.keepgardeningproject_seller.UI.MyPageSellerQnADetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.Repository.QnARepository

class MyPageSellerQnADetailViewModel : ViewModel() {

    var qnaTitle = MutableLiveData<String>()
    var qnaContent = MutableLiveData<String>()
    var qnaAnswer = MutableLiveData<String>()
    var qnaDate = MutableLiveData<String>()

    var qnaProductType = MutableLiveData<String>()
    var qnaCustomerIdx = MutableLiveData<Long>()
    var qnaProductIdx = MutableLiveData<Long>()

    fun getQnAInfo(qnaIdx: Long) {

        QnARepository.getQnAInfoByIdx(qnaIdx) {
            for (c1 in it.result.children) {
                qnaProductType.value = c1.child("qnAProductType").value as String
                qnaCustomerIdx.value = c1.child("qnACustomerIdx").value as Long
                qnaProductIdx.value = c1.child("qnAProductIdx").value as Long
                qnaTitle.value = c1.child("qnATitle").value as String
                qnaContent.value = c1.child("qnAContent").value as String
                qnaAnswer.value = c1.child("qnAAnswer").value as String
                qnaDate.value = c1.child("qnADate").value as String
            }
        }
    }
}