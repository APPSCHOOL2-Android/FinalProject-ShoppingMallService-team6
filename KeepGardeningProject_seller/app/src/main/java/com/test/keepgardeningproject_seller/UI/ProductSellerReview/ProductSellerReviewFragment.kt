package com.test.keepgardeningproject_seller.UI.ProductSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.DAO.QnAClass
import com.test.keepgardeningproject_seller.DAO.ReviewClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment.Companion.productIdx
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAViewModel
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerReviewBinding
import com.test.keepgardeningproject_seller.databinding.RowProductSellerReviewBinding

class ProductSellerReviewFragment : Fragment() {

    lateinit var fragmentProductSellerReviewBinding: FragmentProductSellerReviewBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = ProductSellerReviewFragment()
    }

    private lateinit var viewModel: ProductSellerReviewViewModel

    var reviewList = mutableListOf<ReviewClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerReviewBinding = FragmentProductSellerReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[ProductSellerReviewViewModel::class.java]
        viewModel.run {
            reviewClassList.observe(mainActivity) {
                reviewList = it
                fragmentProductSellerReviewBinding.recyclerViewProductSellerReview.adapter?.notifyDataSetChanged()
                fragmentProductSellerReviewBinding.textViewProductSellerReview.text = "리뷰 내역 : ${reviewList.size}개"
            }
        }
        viewModel.getReviewInfoAll(productIdx.toLong())

        fragmentProductSellerReviewBinding.run {

            recyclerViewProductSellerReview.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
                adapter?.notifyDataSetChanged()
            }
        }
        return fragmentProductSellerReviewBinding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getReviewInfoAll(productIdx.toLong())

        var adapter =
            fragmentProductSellerReviewBinding.recyclerViewProductSellerReview.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()

        fragmentProductSellerReviewBinding.root.requestLayout()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowProductSellerReviewBinding: RowProductSellerReviewBinding) : RecyclerView.ViewHolder(rowProductSellerReviewBinding.root) {

            var textViewRowReviewTitle : TextView
            var ratingBarRow : RatingBar
            var textViewRowReviewContent : TextView

            init {
                textViewRowReviewTitle = rowProductSellerReviewBinding.textViewProductSellerReviewReviewTitle
                ratingBarRow = rowProductSellerReviewBinding.ratingBarProductSellerReview
                textViewRowReviewContent = rowProductSellerReviewBinding.textViewProductSellerReviewReviewContent
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowProductSellerReviewBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return reviewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowReviewTitle.text = reviewList[position].reviewTitle
            holder.textViewRowReviewContent.text = reviewList[position].reviewContent
            holder.ratingBarRow.rating = reviewList[position].rating.toFloat()
        }
    }

}