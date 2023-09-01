package com.test.keepgardeningproject_seller.UI.MyPageSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.QnARepository
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerReviewBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerReviewBinding
import java.net.URL

class MyPageSellerReviewFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerReviewFragment()
    }

    private lateinit var viewModel: MyPageSellerReviewViewModel

    lateinit var binding : FragmentMyPageSellerReviewBinding

    lateinit var mainActivity:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerReviewBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        var useridx = mainActivity.loginSellerInfo.userSellerStoreName.toString()
       viewModel = ViewModelProvider(mainActivity)[MyPageSellerReviewViewModel::class.java]
        viewModel.run {
            reviewlist.observe(mainActivity){
                binding.recyclerViewSellerReview.adapter?.notifyDataSetChanged()
            }
        }
        binding.run{

            viewModel.getData(useridx)
            materialToolbarSellerReview.run{

                title = "리뷰내역"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    viewModel.resetList()
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_REVIEW_FRAGMENT)
                }

            }

            recyclerViewSellerReview.run{

                adapter = ReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

        }

        return binding.root
    }



    inner class ReviewRecyclerViewAdapter : RecyclerView.Adapter<ReviewRecyclerViewAdapter.AllViewHolder>(){
        inner class AllViewHolder(rowPostListBinding: RowMyPageSellerReviewBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListStoreName: TextView
            val rowPostImg :ImageView
            var rowPostProductName:TextView
            val rowPostComment:TextView
            var rowArrow :ImageButton
            var ratingbar:RatingBar

            init{

                rowPostListStoreName = rowPostListBinding.textviewRsStoreName
                rowPostProductName = rowPostListBinding.textviewRsProductName
                rowPostComment = rowPostListBinding.textviewRsProductComment
                rowArrow  = rowPostListBinding.buttonRsDetail
                rowPostImg = rowPostListBinding.imageviewRsImg
                ratingbar = rowPostListBinding.ProductReviewStars
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRecyclerViewAdapter.AllViewHolder {
            val rowPostListBinding = RowMyPageSellerReviewBinding.inflate(layoutInflater)
            val allViewHolder = AllViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun onBindViewHolder(holder: AllViewHolder, position: Int) {

            holder.rowPostListStoreName.text = viewModel.reviewlist.value!!.get(position).reviewUserName
            holder.ratingbar.rating = viewModel.reviewlist.value!!.get(position).reviewRating.toFloat()
            holder.rowPostProductName.text = viewModel.reviewlist.value!!.get(position).reviewProductName
            holder.rowPostComment.text = viewModel.reviewlist.value!!.get(position).reviewTitle
            QnARepository.getQnaImage(viewModel.reviewlist.value?.get(position)?.reviewImg.toString()){

                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.rowPostImg)
            }

        }

        override fun getItemCount(): Int {
            return viewModel.reviewlist.value?.size!!
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetList()
    }
}