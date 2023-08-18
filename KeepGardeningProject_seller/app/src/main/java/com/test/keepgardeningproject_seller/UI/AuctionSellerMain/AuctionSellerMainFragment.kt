package com.test.keepgardeningproject_seller.UI.AuctionSellerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.AuctionSellerDetail.AuctionSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerInfo.AuctionSellerInfoFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerQnA.AuctionSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerDetail.ProductSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerMainBinding

class AuctionSellerMainFragment : Fragment() {

    lateinit var fragmentAuctionSellerMainBinding: FragmentAuctionSellerMainBinding
    lateinit var mainActivity: MainActivity

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "상세정보","경매정보","문의"
    )

    companion object {
        fun newInstance() = AuctionSellerMainFragment()
    }

    private lateinit var viewModel: AuctionSellerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerMainBinding = FragmentAuctionSellerMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentList.add(AuctionSellerDetailFragment())
        fragmentList.add(AuctionSellerInfoFragment())
        fragmentList.add(AuctionSellerQnAFragment())

        fragmentAuctionSellerMainBinding.run {

            viewPagerAuctionSellerMainFragment.adapter = TabAdapterClass(mainActivity)

                // 탭 구성
                val tabLayoutMediator = TabLayoutMediator(tabAuctionSellerMain, viewPagerAuctionSellerMainFragment){ tab: TabLayout.Tab, i: Int ->
                    tab.text = tabName[i]
                }
                tabLayoutMediator.attach()


            buttonAuctionSellerMainEdit.setOnClickListener {
                mainActivity.replaceFragment(AUCTION_SELLER_EDIT_FRAGMENT, true, null)
            }
        }
        return fragmentAuctionSellerMainBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerMainViewModel::class.java)
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