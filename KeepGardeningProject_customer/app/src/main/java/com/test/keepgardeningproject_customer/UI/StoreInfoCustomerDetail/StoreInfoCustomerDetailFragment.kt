package com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentStoreInfoCustomerDetailBinding

class StoreInfoCustomerDetailFragment : Fragment() {
    lateinit var fragmentStoreInfoCustomerDetailBinding: FragmentStoreInfoCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: StoreInfoCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStoreInfoCustomerDetailBinding = FragmentStoreInfoCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentStoreInfoCustomerDetailBinding.run {
            toolbarStoreInfoDetail.run {
                title = "스토어 상세"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STORE_INFO_CUSTOMER_DETAIL_FRAGMENT)
                }
            }
        }

        return fragmentStoreInfoCustomerDetailBinding.root
    }
}