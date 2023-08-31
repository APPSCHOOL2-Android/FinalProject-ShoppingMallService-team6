package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewDetail

import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewDetailBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class MyPageCustomerReviewDetailFragment : Fragment() {

    private lateinit var viewModel: MyPageCustomerReviewDetailViewModel
    lateinit var binding: FragmentMyPageCustomerReviewDetailBinding
    lateinit var mainActivity: MainActivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageCustomerReviewDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[MyPageCustomerReviewDetailViewModel::class.java]
        viewModel.run {
            reviewProductImage.observe(mainActivity) {
                binding.imageViewRcDetailProductImage.setImageBitmap(it)
            }
            reviewProductName.observe(mainActivity) {
                binding.textviewRcDetailProductName.text = it
            }
            reviewStoreName.observe(mainActivity) {
                binding.textviewRcDetailStoreName.text = it
            }
            reviewRating.observe(mainActivity) {
                binding.ratingbarRcReviewDetail.rating = it.toFloat()
            }
            reviewTitle.observe(mainActivity) {
                binding.editTextViewRcTitle.setText(it)
            }
            reviewContent.observe(mainActivity) {
                binding.editTextViewRcContent.setText(it)
            }
        }

        binding.run {
            materialToolbarRcDetail.run {
                title = "리뷰 세부 내역"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAGMENT)
                }
            }

            imageViewRcDetailProductImage.setImageResource(R.mipmap.app)
            textviewRcDetailStoreName.text = ""
            textviewRcDetailProductName.text = ""

            editTextViewRcTitle.setTextColor(Color.BLACK)
            editTextViewRcContent.setTextColor(Color.BLACK)

            val reviewIdx = arguments?.getLong("reviewIdx")
            viewModel.getReviewDetail(reviewIdx!!)

            buttonRcDetailDelete.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mainActivity).apply {
                    setTitle("리뷰 삭제")
                    setMessage("리뷰를 삭제하시겠습니까?")
                    setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                        ReviewRepository.removeReviewInfo(reviewIdx) {
                            mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAGMENT)
                        }
                    }
                    setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->
                    }
                }
                builder.show()
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.reset()
    }
}