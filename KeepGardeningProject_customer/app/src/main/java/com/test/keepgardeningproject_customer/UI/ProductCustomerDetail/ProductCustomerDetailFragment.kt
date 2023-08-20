package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailBottomDialog
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailDetailFragment
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailReviewFragment
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailBinding

class ProductCustomerDetailFragment : Fragment() {

    lateinit var fragmentProductCustomerDetailBinding: FragmentProductCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var productCustomerDetailDetailFragment : ProductCustomerDetailDetailFragment
    lateinit var productCustomerDetailReviewFragment : ProductCustomerDetailReviewFragment

    // 탭
    val tabName = arrayOf(
        "상세정보", "리뷰"
    )

    val fragmentList = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductCustomerDetailBinding = FragmentProductCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        productCustomerDetailDetailFragment = ProductCustomerDetailDetailFragment()
        productCustomerDetailReviewFragment = ProductCustomerDetailReviewFragment()

        fragmentProductCustomerDetailBinding.run{
            // 툴바
            toolbarPcd.run{
                title = "상품상세정보"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT)
                }
            }

            // 상품 이미지 뷰페이저

            // 탭레이아웃
            fragmentList.add(productCustomerDetailDetailFragment)
            fragmentList.add(productCustomerDetailReviewFragment)

            viewPagerPcdTab.adapter = TabAdapterClass(mainActivity)
            val tabLayoutMediator = TabLayoutMediator(tabsPcd, viewPagerPcdTab){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()

            when(tabsPcd.selectedTabPosition){
                0->{
                    viewPagerPcdTab.layoutParams.height = 10000
                }
                1->{
                    viewPagerPcdTab.layoutParams.height = 1000
                }
            }

            // 구매완료 버튼
            buttonPcdBuy.setOnClickListener{
                val bs = ProductCustomerDetailBottomDialog()
                bs.show(mainActivity.supportFragmentManager,"구매")
            }

        }

        return fragmentProductCustomerDetailBinding.root
    }

    inner class TabAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}