package com.test.keepgardeningproject_seller.UI.HomeSeller

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
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.AuctionSellerRegister.AuctionSellerRegisterFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerRegister.ProductSellerRegisterFragment
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerTabBinding

class HomeSellerTabFragment : Fragment() {

//    private lateinit var viewModel: HomeSellerViewModel

    lateinit var fragmentHomeSellerTabBinding: FragmentHomeSellerTabBinding
    lateinit var mainActivity: MainActivity

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "판매","경매"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeSellerTabBinding = FragmentHomeSellerTabBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentList.add(HomeSellerProductFragment())
        fragmentList.add(HomeSellerAuctionFragment())

        fragmentHomeSellerTabBinding.run {
//            viewPagerHomeSeller.setUserInputEnabled(false)
            viewPagerHomeSellerTab.adapter = TabAdapterClass(mainActivity)

            // 탭 구성
            val tabLayoutMediator = TabLayoutMediator(tabHomeSellerTab, viewPagerHomeSellerTab){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()

        }
        return fragmentHomeSellerTabBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentHomeSellerTabBinding.viewPagerHomeSellerTab.requestLayout()
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