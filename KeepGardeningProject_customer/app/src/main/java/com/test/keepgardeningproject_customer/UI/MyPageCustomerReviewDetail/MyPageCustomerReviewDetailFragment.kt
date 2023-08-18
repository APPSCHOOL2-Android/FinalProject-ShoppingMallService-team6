package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewDetailBinding


class MyPageCustomerReviewDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewDetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerReviewDetailViewModel

    lateinit var binding:FragmentMyPageCustomerReviewDetailBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerReviewDetailBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarRcDetail.run{

                title = "리뷰 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAMGNET)

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }


}