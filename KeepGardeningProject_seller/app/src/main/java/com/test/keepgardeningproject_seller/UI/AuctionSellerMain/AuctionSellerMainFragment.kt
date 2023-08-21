package com.test.keepgardeningproject_seller.UI.AuctionSellerMain

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_INFO_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_QNA_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.AuctionSellerDetail.AuctionSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerInfo.AuctionSellerInfoFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerQnA.AuctionSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerDetail.ProductSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ViewPager2Adapter
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerMainBinding

class AuctionSellerMainFragment : Fragment() {

    lateinit var fragmentAuctionSellerMainBinding: FragmentAuctionSellerMainBinding
    lateinit var mainActivity: MainActivity

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

        fragmentAuctionSellerMainBinding.run {

            toolbarAuctionSellerMain.run {
                title = "경매 상품 정보"

                setNavigationIcon(R.drawable.ic_back_24px)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    var oldFragment = arguments?.getString("oldFragment")
                    if(oldFragment == "AuctionSellerRegisterFragment") {
                        mainActivity.removeFragment(MainActivity.AUCTION_SELLER_REGISTER_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT)
                    } else {
                        mainActivity.removeFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT)
                    }
                }
            }


            buttonAuctionSellerMainEdit.setOnClickListener {
                mainActivity.replaceFragment(AUCTION_SELLER_EDIT_FRAGMENT, true, null)
            }

            tabAuctionSellerMain.run {
                //ViewPager2 Adapter 셋팅
                var viewPager2Adatper = ViewPager2Adapter(mainActivity)
                viewPager2Adatper.addFragment(AuctionSellerDetailFragment())
                viewPager2Adatper.addFragment(AuctionSellerInfoFragment())
                viewPager2Adatper.addFragment(AuctionSellerQnAFragment())

                //Adapter 연결
                fragmentAuctionSellerMainBinding.viewPagerAuctionSellerMainFragment.apply {

                    adapter = viewPager2Adatper


                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                        }
                    })
                }

                //ViewPager, TabLayout 연결
                TabLayoutMediator(
                    tabAuctionSellerMain,
                    viewPagerAuctionSellerMainFragment
                ) { tab, position ->
                    when (position) {
                        0 -> tab.text = "상세정보"
                        1 -> tab.text = "경매정보"
                        2 -> tab.text = "문의"
                    }
                }.attach()
            }
            return fragmentAuctionSellerMainBinding.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }


    }

}