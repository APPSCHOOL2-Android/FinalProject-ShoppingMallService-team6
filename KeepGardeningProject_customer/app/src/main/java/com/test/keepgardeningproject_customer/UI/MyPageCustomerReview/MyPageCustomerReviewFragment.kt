package com.test.keepgardeningproject_customer.UI.MyPageCustomerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerQnaBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerReviewBinding

class MyPageCustomerReviewFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewFragment()
    }

    private lateinit var viewModel: MyPageCustomerReviewViewModel

    lateinit var binding:FragmentMyPageCustomerReviewBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerReviewBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            MyPageReviewRecyclerView.run{

                adapter = ReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class ReviewRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(rowCustomerReviewBinding: RowMyPageCustomerReviewBinding) :
            RecyclerView.ViewHolder(rowCustomerReviewBinding.root) {

            val ProductName: TextView
            val StoreName: TextView
            val Comment: TextView

            init {
                ProductName = rowCustomerReviewBinding.textviewRcProductName
                StoreName = rowCustomerReviewBinding.textviewRcStoreName
                Comment = rowCustomerReviewBinding.textviewRcProductComment
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowCustomerReviewBinding = RowMyPageCustomerReviewBinding.inflate(layoutInflater)
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

                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAMGNET,true,null)

            }

        }
    }

}