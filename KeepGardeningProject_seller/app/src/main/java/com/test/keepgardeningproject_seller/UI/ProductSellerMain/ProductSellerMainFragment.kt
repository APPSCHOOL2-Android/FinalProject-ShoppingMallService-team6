package com.test.keepgardeningproject_seller.UI.ProductSellerMain

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.HomeSeller.HomeSellerAuctionFragment
import com.test.keepgardeningproject_seller.UI.HomeSeller.HomeSellerProductFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerDetail.ProductSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerMainBinding

class ProductSellerMainFragment : Fragment() {

    lateinit var fragmentProductSellerMainBinding: FragmentProductSellerMainBinding
    lateinit var mainActivity: MainActivity

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "상세정보","리뷰","문의"
    )

    companion object {
        fun newInstance() = ProductSellerMainFragment()
    }

    private lateinit var viewModel: ProductSellerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerMainBinding = FragmentProductSellerMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentList.add(ProductSellerDetailFragment())
        fragmentList.add(ProductSellerReviewFragment())
        fragmentList.add(ProductSellerQnAFragment())

        fragmentProductSellerMainBinding.run {
            viewPagerProductSellerMainFragment.adapter = TabAdapterClass(mainActivity)

            // 탭 구성
            val tabLayoutMediator = TabLayoutMediator(tabProductSellerMain, viewPagerProductSellerMainFragment){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()


        }


        return fragmentProductSellerMainBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    // adapter 클래스
    inner class TabAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }

}