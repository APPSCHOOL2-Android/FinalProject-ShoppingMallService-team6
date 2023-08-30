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
import com.test.keepgardeningproject_customer.DAO.Review
import com.test.keepgardeningproject_customer.DAO.TestDAO
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
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

    lateinit var reviewList:MutableList<Review>

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

            val userIdx = MainActivity.loginedUserInfo.userIdx.toString()

            ReviewRepository.getReviewByuserIdx(userIdx){

                for(c1 in it.result.children){

                    reviewList.add(c1.value as Review)

                }

                //Review에서 useridx기준으로 데이터를 뽑아와서 reviewList에 넣고, 이를 리사이클러뷰로 보여준다.

                MyPageReviewRecyclerView.run{

                    adapter = ReviewRecyclerViewAdapter(reviewList)
                    layoutManager = LinearLayoutManager(context)

                    adapter?.notifyDataSetChanged()

                }

            }





        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class ReviewRecyclerViewAdapter(val reviewList: MutableList<Review>) :
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
            holder.ProductName.text = reviewList[position].productName
            holder.StoreName.text = reviewList[position].storeName
            holder.Comment.text = reviewList[position].reviewTitle
            holder.rating.rating = reviewList[position].rating

            holder.moveBtn.setOnClickListener {

                val imageResourceId = R.drawable.img_orchid

                newBundle = Bundle().apply{

                    putFloat("contentRating",reviewList[position].rating)
                    putInt("contentImage",imageResourceId)
                    putString("contentTitle",reviewList[position].reviewTitle)
                    putString("contentReview",reviewList[position].reviewContent)

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