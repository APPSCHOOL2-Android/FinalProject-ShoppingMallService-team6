package com.test.keepgardeningproject_seller.UI.AuctionSellerQnA

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.DAO.QnAClass
import com.test.keepgardeningproject_seller.Repository.QnARepository

class AuctionSellerQnAViewModel : ViewModel() {

    // 전체 문의 목록
    var qnaClassList = MutableLiveData<MutableList<QnAClass>>()

    init {
        qnaClassList.value = mutableListOf<QnAClass>()
    }


    val tempList = mutableListOf<QnAClass>()


    fun getQnAInfoAll(productIdx: Long) {

        QnARepository.getQnAInfoAllByProduct(productIdx) {
            for (c1 in it.result.children) {
                val qnaIdx = c1.child("qnAIdx").value as Long
                val qnaProductType = c1.child("qnAProductType").value as String
                val qnaCustomerIdx = c1.child("qnACustomerIdx").value as Long
                val qnaStoreIdx = c1.child("qnAStoreIdx").value as Long
                val qnaTitle = c1.child("qnATitle").value as String
                val qnaContent = c1.child("qnAContent").value as String
                val qnaAnswer = c1.child("qnAAnswer").value as String
                val qnaDate = c1.child("qnADate").value as String

                Log.d("lion","$qnaProductType, $productIdx")
                val q1 = QnAClass(
                    qnaIdx,
                    qnaProductType,
                    productIdx,
                    qnaCustomerIdx,
                    qnaStoreIdx,
                    qnaTitle,
                    qnaContent,
                    qnaAnswer,
                    qnaDate
                )
                tempList.add(q1)

            }

            // 가장 마지막에 등록한것부터 보여주기
            tempList.reverse()

            qnaClassList.value = tempList

        }
    }
}