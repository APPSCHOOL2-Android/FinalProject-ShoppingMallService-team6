package com.test.keepgardeningproject_seller.UI.MyPageSellerReviewDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class MyPageSellerReviewDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerReviewDetailFragment()
    }

    private lateinit var viewModel: MyPageSellerReviewDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_seller_review_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerReviewDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}