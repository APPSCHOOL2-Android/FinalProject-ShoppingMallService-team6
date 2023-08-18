package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerQnADetailBinding

class MyPageCustomerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerQnADetailViewModel

    lateinit var binding:FragmentMyPageCustomerQnADetailBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerQnADetailBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarQcDetail.run{

                title = "문의 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT)

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}