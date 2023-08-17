package com.test.keepgardeningproject_seller.UI.MyPageSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerReviewBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerReviewBinding

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

        val view = binding.root


        binding.run{

            recyclerViewSellerReview.run{

                adapter = ReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class ReviewRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(rowCustomerReviewBinding: RowMyPageSellerReviewBinding) :
            RecyclerView.ViewHolder(rowCustomerReviewBinding.root) {

            val ProductName: TextView
            val StoreName: TextView
            val Comment: TextView

            init {
                ProductName = rowCustomerReviewBinding.textviewRcProductName
                StoreName = rowCustomerReviewBinding.textviewRsStoreName
                Comment = rowCustomerReviewBinding.textviewRsProductComment
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowCustomerReviewBinding = RowMyPageSellerReviewBinding.inflate(layoutInflater)
            val ViewHolder = ReviewViewHolder(rowCustomerReviewBinding)

            rowCustomerReviewBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            holder.ProductName.text = "스파이더맨"

            holder.itemView.setOnClickListener {

                mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_REVIEW_FRAGMNET,true,null)

            }

        }
    }


}