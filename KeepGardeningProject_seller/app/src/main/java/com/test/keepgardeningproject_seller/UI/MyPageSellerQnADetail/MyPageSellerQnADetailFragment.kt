package com.test.keepgardeningproject_seller.UI.MyPageSellerQnADetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerQnADetailBinding

class MyPageSellerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageSellerQnADetailViewModel

    lateinit var binding:FragmentMyPageSellerQnADetailBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerQnADetailBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarQsDetail.run{

                title = "문의 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT)

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}