package com.test.keepgardeningproject_seller.UI.MyPageSellerQnA

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerQnABinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerQuaBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerReviewBinding

class MyPageSellerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerQnAFragment()
    }

    private lateinit var viewModel: MyPageSellerQnAViewModel

    lateinit var binding: FragmentMyPageSellerQnABinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerQnABinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarSellerQuestion.run{

                title = "문의내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_QNA_FRAGMENT)

                }

            }

            recyclerViewSellerQuestion.run{

                adapter = QuestionRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerQnAViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class QuestionRecyclerViewAdapter :
        RecyclerView.Adapter<QuestionRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(rowCustomerQnABinding: RowMyPageSellerQuaBinding) :
            RecyclerView.ViewHolder(rowCustomerQnABinding.root) {

            val ProductName: TextView
            val StoreName: TextView
            val Comment: TextView

            val detailBtn: ImageView


            init {

                ProductName = rowCustomerQnABinding.textviewQsProductName
                StoreName = rowCustomerQnABinding.textviewQsStoreName
                Comment = rowCustomerQnABinding.textviewQsReplyState
                detailBtn = rowCustomerQnABinding.buttonQsDetail
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowCustomerReviewBinding = RowMyPageSellerQuaBinding.inflate(layoutInflater)
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

            holder.detailBtn.setOnClickListener {

                mainActivity.replaceFragment(
                    MainActivity.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT,
                    true,
                    null
                )

            }

        }
    }

}