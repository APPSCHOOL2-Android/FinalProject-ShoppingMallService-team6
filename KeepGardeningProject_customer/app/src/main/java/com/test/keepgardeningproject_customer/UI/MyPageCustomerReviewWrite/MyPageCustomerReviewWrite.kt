package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewWriteBinding

class MyPageCustomerReviewWrite : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewWrite()
    }

    private lateinit var viewModel: MyPageCustomerReviewWriteViewModel

    lateinit var binding:FragmentMyPageCustomerReviewWriteBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerReviewWriteBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarRcWrite.run{

                title = "구매내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewWriteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}