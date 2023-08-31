package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_customer.DAO.Review
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewWriteBinding

class MyPageCustomerReviewWrite : Fragment() {
    private lateinit var viewModel: MyPageCustomerReviewWriteViewModel
    lateinit var binding:FragmentMyPageCustomerReviewWriteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageCustomerReviewWriteBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[MyPageCustomerReviewWriteViewModel::class.java]
        viewModel.run {
            reviewStoreName.observe(mainActivity) {
                binding.textviewRcWriteStoreName.text = it
            }
            reviewProductName.observe(mainActivity) {
                binding.textviewRcWriteProductName.text = it
            }
            reviewProductImage.observe(mainActivity) {
                binding.imageViewRcWriteProductImage.setImageBitmap(it)
            }
        }

        binding.run{
            // 사진이 로딩되기 전까지 보여줄 기본 이미지 설정
            imageViewRcWriteProductImage.setImageResource(R.mipmap.app)

            materialToolbarRcWrite.run{
                title = "리뷰내역"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)
                }
            }

            buttonRcWrite.run{
                setOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)
                }
            }

            //사용자 인덱스 추출
            val userIdx = MainActivity.loginedUserInfo.userIdx.toString()

            //제품 이름 송신(bundle)
            val productIdx = arguments?.getLong("ordersProductIdx")!!

            //리뷰 분기할 인덱스 송신(bundle)
            val ordersIdx = arguments?.getLong("ordersIdx")!!

            // 리뷰 상품 정보 받아오기
            viewModel.getProductInfo(productIdx)

            // 리뷰 작성 버튼 클릭
            buttonRcWrite.run {
                setOnClickListener {
                    val reviewTitle = editTextViewRcTitle.text.toString()
                    val reviewContent = editTextViewRcContent.text.toString()
                    val reviewRating = ratingbarRcReviewDetail.rating.toLong()

                    //입력요소에 대한 유효성 검사.
                    if (reviewTitle.isEmpty()) {
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("입력오류")
                        builder.setMessage("리뷰제목을 입력하세요")
                        builder.setIcon(R.drawable.ic_close_24px)
                        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                            editTextViewRcTitle.requestFocus()
                        }
                        builder.show()
                        return@setOnClickListener

                    }
                    if (reviewContent.isEmpty()) {
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("입력 오류")
                        builder.setMessage("리뷰를 입력하세요")
                        builder.setIcon(R.drawable.ic_close_24px)
                        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                            editTextViewRcContent.requestFocus()
                        }
                        builder.show()
                        return@setOnClickListener
                    }

                    ReviewRepository.getReviewIdx {
                        var reviewIdx = it.result.value as Long
                        reviewIdx++

                        val review = Review(
                            reviewIdx,
                            userIdx,
                            productIdx,
                            textviewRcWriteProductName.text.toString(),
                            textviewRcWriteStoreName.text.toString(),
                            reviewRating,
                            reviewTitle,
                            reviewContent
                        )

                        ReviewRepository.addReviewInfo(review) {
                            ReviewRepository.setReviewIdx(reviewIdx) {
                                mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }
}