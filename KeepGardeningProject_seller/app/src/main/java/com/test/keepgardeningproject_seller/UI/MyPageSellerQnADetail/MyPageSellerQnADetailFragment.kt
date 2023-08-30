package com.test.keepgardeningproject_seller.UI.MyPageSellerQnADetail

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.AuctionProductClass
import com.test.keepgardeningproject_seller.DAO.QnAClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.QnARepository
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainFragment.Companion.auctionProductIdx
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerQnADetailBinding

class MyPageSellerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageSellerQnADetailViewModel
    lateinit var fragmentMyPageSellerQnADetailBinding: FragmentMyPageSellerQnADetailBinding
    lateinit var mainActivity: MainActivity

    var qnaIdx:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMyPageSellerQnADetailBinding = FragmentMyPageSellerQnADetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        qnaIdx = arguments?.getInt("qnaIdx", 0)!!
        Log.d("lion","qnaIdx : $qnaIdx")

        viewModel = ViewModelProvider(mainActivity)[MyPageSellerQnADetailViewModel::class.java]
        viewModel.run {

            qnaTitle.observe(mainActivity) {
                fragmentMyPageSellerQnADetailBinding.editTextViewQsDetailTitle.setText(it)
            }
            qnaContent.observe(mainActivity) {
                fragmentMyPageSellerQnADetailBinding.editTextViewQsDetailContent.setText(it)
            }
            qnaDate.observe(mainActivity) {
                fragmentMyPageSellerQnADetailBinding.textviewQcDetailDate.text = it
            }
            qnaAnswer.observe(mainActivity) {
                if(it == "None") {
                    fragmentMyPageSellerQnADetailBinding.run {
                        textviewQcDetailReplyState.run {
                            text = "미답변"
                            setTextColor(resources.getColor(R.color.Red))
                        }
                        buttonQsAnswerChange.text = "답변 등록하기"
                        editTextViewQsDetailAnswer.setText("")
                    }
                }
                else {
                    fragmentMyPageSellerQnADetailBinding.run {
                        textviewQcDetailReplyState.run {
                            text = "답변 완료"
                            setTextColor(resources.getColor(R.color.colorAccent3))
                        }
                        editTextViewQsDetailAnswer.setText(it)
                        buttonQsAnswerChange.text = "답변 수정하기"
                    }
                }
            }
        }
        viewModel.getQnAInfo(qnaIdx.toLong())

        fragmentMyPageSellerQnADetailBinding.run{

            materialToolbarQsDetail.run{

                title = "문의 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT)

                }

            }

            editTextViewQsDetailTitle.run {
                isEnabled = false
                setTextColor(Color.BLACK)
            }
            editTextViewQsDetailContent.run {
                isEnabled = false
                setTextColor(Color.BLACK)
            }

            buttonQsAnswerChange.setOnClickListener {
                val qnaDataClass = QnAClass(
                    qnaIdx.toLong(),
                    viewModel.qnaProductType.value.toString(),
                    viewModel.qnaProductIdx.value!!.toLong(),
                    viewModel.qnaCustomerIdx.value!!.toLong(),
                    mainActivity.loginSellerInfo.userSellerIdx,
                    viewModel.qnaTitle.value.toString(),
                    viewModel.qnaContent.value.toString(),
                    editTextViewQsDetailAnswer.text.toString(),
                    viewModel.qnaDate.value.toString()
                )

                // 문의 답변 저장
                QnARepository.modifyQnAAnswer(qnaDataClass) {
                    Log.d("lion","$qnaDataClass")
                }

//                viewModel.getQnAInfo(qnaIdx.toLong())

                Snackbar.make(fragmentMyPageSellerQnADetailBinding.root, "문의 답변이 등록되었습니다.", Snackbar.LENGTH_SHORT).show()
                mainActivity.removeFragment(MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT)
            }
        }

        return fragmentMyPageSellerQnADetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getQnAInfo(qnaIdx.toLong())
    }

}