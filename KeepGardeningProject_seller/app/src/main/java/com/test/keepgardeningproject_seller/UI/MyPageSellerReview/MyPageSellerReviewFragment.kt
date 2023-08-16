package com.test.keepgardeningproject_seller.UI.MyPageSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class MyPageSellerReviewFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerReviewFragment()
    }

    private lateinit var viewModel: MyPageSellerReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_seller_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}