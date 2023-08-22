package com.test.keepgardeningproject_customer.UI.MyPageCustomerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.DAO.MypageReview
import com.test.keepgardeningproject_customer.DAO.MypageReviewDetail
import com.test.keepgardeningproject_customer.DAO.TestDAO
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerReviewBinding
import org.w3c.dom.Text

class MyPageCustomerReviewFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewFragment()
    }

    private lateinit var viewModel: MyPageCustomerReviewViewModel

    lateinit var binding: FragmentMyPageCustomerReviewBinding

    lateinit var mainActivity: MainActivity

    lateinit var newBundle:Bundle

    var reviewDetail: MypageReviewDetail = MypageReviewDetail(1,"오준용","환불할게요")

    var MypageReview = MypageReview("DC","조커",3.0f, reviewDetail)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerReviewBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run {

            materialToolbarRc.run {

                title = "리뷰 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_FRAGMENT)

                }

            }

            MyPageReviewRecyclerView.run {

                adapter = ReviewRecyclerViewAdapter(MypageReview)
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

    inner class ReviewRecyclerViewAdapter(val review: MypageReview) :
        RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(rowCustomerReviewBinding: RowMyPageCustomerReviewBinding) :
            RecyclerView.ViewHolder(rowCustomerReviewBinding.root) {

            val ProductName: TextView
            val StoreName: TextView
            val Comment: TextView

            val rating :RatingBar

            val moveBtn:ImageButton

            init {
                ProductName = rowCustomerReviewBinding.textviewRcProductName
                StoreName = rowCustomerReviewBinding.textviewRcStoreName
                Comment = rowCustomerReviewBinding.textviewRcProductComment

                rating = rowCustomerReviewBinding.ProductReviewStars

                moveBtn = rowCustomerReviewBinding.imageButtonRcDetail
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
            holder.ProductName.text = MypageReview.productName
            holder.StoreName.text = MypageReview.storeName
            holder.Comment.text = MypageReview.content.reviewTitle
            holder.rating.rating = MypageReview.ratings

            holder.moveBtn.setOnClickListener {

                val imageResourceId = R.drawable.img_orchid

                newBundle = Bundle().apply{

                    putFloat("contentRating",MypageReview.ratings)
                    putInt("contentImage",imageResourceId)
                    putString("contentTitle",MypageReview.content.reviewTitle)
                    putString("contentReview",MypageReview.content.reviewContent)

                }


                mainActivity.replaceFragment(
                    MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAGMENT,
                    true,
                    newBundle
                )

            }

        }
    }

}