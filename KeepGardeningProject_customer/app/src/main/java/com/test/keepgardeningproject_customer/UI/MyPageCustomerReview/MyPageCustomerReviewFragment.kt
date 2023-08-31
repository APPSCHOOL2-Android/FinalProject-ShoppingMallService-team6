package com.test.keepgardeningproject_customer.UI.MyPageCustomerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerReviewBinding

class MyPageCustomerReviewFragment : Fragment() {

    private lateinit var viewModel: MyPageCustomerReviewViewModel
    lateinit var binding: FragmentMyPageCustomerReviewBinding
    lateinit var mainActivity: MainActivity

    val userIdx = MainActivity.loginedUserInfo.userIdx!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageCustomerReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[MyPageCustomerReviewViewModel::class.java]
        viewModel.run {
            reviewList.observe(mainActivity) {
                binding.recyclerViewMyPageCustomerReview.adapter?.notifyDataSetChanged()
            }
        }

        binding = FragmentMyPageCustomerReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        binding.run {
            viewModel.reset()
            
            materialToolbarRc.run {
                title = "리뷰 내역"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_FRAGMENT)
                }
            }

            recyclerViewMyPageCustomerReview.run {
                adapter = ReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
            }

            viewModel.getReviewData(userIdx.toString())
        }
        return binding.root
    }

    inner class ReviewRecyclerViewAdapter() :
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(rowCustomerReviewBinding: RowMyPageCustomerReviewBinding) :
            RecyclerView.ViewHolder(rowCustomerReviewBinding.root) {

            val rowImage: ImageView
            val rowStoreName: TextView
            val rowProductName: TextView
            val rowReviewTitle: TextView
            val rowRating :RatingBar

            init {
                rowImage = rowCustomerReviewBinding.imageViewRcProductImage
                rowProductName = rowCustomerReviewBinding.textviewRcProductName
                rowStoreName = rowCustomerReviewBinding.textviewRcStoreName
                rowReviewTitle = rowCustomerReviewBinding.textviewRcProductComment
                rowRating = rowCustomerReviewBinding.ProductReviewStars

                rowCustomerReviewBinding.root.setOnClickListener {
                    val reviewIdx = viewModel.reviewList.value?.get(adapterPosition)?.reviewIdx!!
                    val bundle = Bundle()
                    bundle.putLong("reviewIdx", reviewIdx)

                    mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAGMENT, true, bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowCustomerReviewBinding = RowMyPageCustomerReviewBinding.inflate(layoutInflater)
            val viewHolder = ReviewViewHolder(rowCustomerReviewBinding)

            rowCustomerReviewBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return viewModel.reviewList.value?.size!!
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            var fileName = viewModel.reviewList.value?.get(position)?.productImage!!
            ReviewRepository.getProductImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowImage)
            }

            holder.rowStoreName.text = viewModel.reviewList.value?.get(position)?.storeName!!
            holder.rowProductName.text = viewModel.reviewList.value?.get(position)?.productName!!
            holder.rowReviewTitle.text = viewModel.reviewList.value?.get(position)?.reviewTitle!!
            holder.rowRating.rating = viewModel.reviewList.value?.get(position)?.reviewRating?.toFloat()!!
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviewData(userIdx.toString())
    }
}