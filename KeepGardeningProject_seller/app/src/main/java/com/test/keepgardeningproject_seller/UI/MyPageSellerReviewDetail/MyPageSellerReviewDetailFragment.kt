package com.test.keepgardeningproject_seller.UI.MyPageSellerReviewDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerReviewDetailBinding

class MyPageSellerReviewDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerReviewDetailFragment()
    }

    private lateinit var viewModel: MyPageSellerReviewDetailViewModel

    lateinit var binding:FragmentMyPageSellerReviewDetailBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerReviewDetailBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarRsDetail.run{

                title = "리뷰 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_REVIEW_DETAIL_FRAGMENT)

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerReviewDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}