package com.test.keepgardeningproject_seller.UI.MyPageSellerQnADetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class MyPageSellerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageSellerQnADetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page_seller_qn_a_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}