package com.test.keepgardeningproject_seller.UI.AuctionSellerMain

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
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
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainViewModel
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ViewPager2Adapter
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AuctionSellerMainFragment : Fragment() {

    lateinit var fragmentAuctionSellerMainBinding: FragmentAuctionSellerMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var auctionSellerMainViewModel: AuctionSellerMainViewModel

    var fileNameList = mutableListOf<String>()
    var date: Date = Calendar.getInstance().time

    private val dataRefreshHandler = Handler()
    private val dataRefreshInterval = 30 * 1000 // 30초 (1분 = 60 * 1000 밀리초)

    val newBundle = Bundle()

    companion object {
        var auctionProductIdx = 0
        fun newInstance() = AuctionSellerMainFragment()
    }

    private lateinit var viewModel: AuctionSellerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerMainBinding = FragmentAuctionSellerMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        auctionProductIdx = arguments?.getInt("auctionProductIdx", 0)!!

        auctionSellerMainViewModel = ViewModelProvider(mainActivity)[AuctionSellerMainViewModel::class.java]
        auctionSellerMainViewModel.run {

            auctionProductName.observe(mainActivity) {
                fragmentAuctionSellerMainBinding.textViewAuctionSellerMainProductName.text = it
            }
            auctionProductOpenPrice.observe(mainActivity) {
                newBundle.putString("auctionProductOpenPrice", it)
                // 숫자 comma 표시하기
                var decimal = DecimalFormat("#,###")
                var temp = it.toInt()
                fragmentAuctionSellerMainBinding.textViewAuctionSellerMainOpenPriceValue.text = decimal.format(temp) + "원"
            }
            auctionProductImageNameList.observe(mainActivity) {
                fileNameList = it
            }
            auctionProductMainImage.observe(mainActivity) {
                fragmentAuctionSellerMainBinding.imageViewAuctionSellerMainMainImage.setImageBitmap(it)
            }
            auctionProductCloseDate.observe(mainActivity) {
                newBundle.putString("auctionProductCloseDate", it)
                newBundle.putString("auctionProductOpenDate", it)
                date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(it)
                var today = Calendar.getInstance()
                var calculateDate = (date.time - today.time.time)

                var calculateDay = calculateDate/(24 * 60 * 60 * 1000)
                var calculateHour = (calculateDate/(60 * 60 * 1000)) - calculateDay*24
                var calculateMinute = (calculateDate/(60 * 1000)) - calculateHour*60 - calculateDay*24*60

                fragmentAuctionSellerMainBinding.textViewAuctionSellerMainTimeValue.text = "${calculateDay}일 ${calculateHour}시 ${calculateMinute}분"

                if(calculateDate < 0) {
                    fragmentAuctionSellerMainBinding.textViewAuctionSellerMainStateValue.text = "입찰 완료"
                } else {
                    fragmentAuctionSellerMainBinding.textViewAuctionSellerMainStateValue.text = "입찰 가능"
                }

                fragmentAuctionSellerMainBinding.textViewAuctionSellerMainPeriodValueOpenDate.text = auctionProductOpenDate.value.toString()
                fragmentAuctionSellerMainBinding.textViewAuctionSellerMainPeriodValueCloseDate.text = auctionProductCloseDate.value.toString()
            }
            auctionSellerMainViewModel.getAuctionProductInfo(auctionProductIdx.toLong())
        }

        // 액티비티가 생성될 때 데이터 갱신 작업 시작
        startDataRefreshTask()

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

            textViewAuctionSellerMainProdcutSellerName.text = mainActivity.loginSellerInfo.userSellerStoreName

            buttonAuctionSellerMainEdit.setOnClickListener {
                mainActivity.replaceFragment(AUCTION_SELLER_EDIT_FRAGMENT, true, newBundle)
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

    override fun onResume() {
        super.onResume()
        fragmentAuctionSellerMainBinding.viewPagerAuctionSellerMainFragment.requestLayout()
    }

    private val dataRefreshRunnable = object : Runnable {
        override fun run() {
            // 데이터를 갱신하는 작업 수행
            fetchDataAndUpdateUI()

            // 다음 주기적 갱신을 예약
            dataRefreshHandler.postDelayed(this, dataRefreshInterval.toLong())
        }
    }

    private fun startDataRefreshTask() {
        // 주기적 갱신 작업을 예약
        dataRefreshHandler.postDelayed(dataRefreshRunnable, dataRefreshInterval.toLong())
    }

    private fun stopDataRefreshTask() {
        // 주기적 갱신 작업을 중단
        dataRefreshHandler.removeCallbacks(dataRefreshRunnable)
    }

    private fun fetchDataAndUpdateUI() {
        // 데이터를 가져와서 UI를 갱신하는 작업 수행
        var today = Calendar.getInstance()
        var calculateDate = (date.time - today.time.time)

        var calculateDay = calculateDate/(24 * 60 * 60 * 1000)
        var calculateHour = (calculateDate/(60 * 60 * 1000)) - calculateDay*24
        var calculateMinute = (calculateDate/(60 * 1000)) - calculateHour*60 - calculateDay*24*60

        fragmentAuctionSellerMainBinding.textViewAuctionSellerMainTimeValue.text = "${calculateDay}일 ${calculateHour}시 ${calculateMinute}분"

        if(calculateDate < 0) {
            fragmentAuctionSellerMainBinding.textViewAuctionSellerMainStateValue.text = "입찰 완료"
        } else {
            fragmentAuctionSellerMainBinding.textViewAuctionSellerMainStateValue.text = "입찰 가능"
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // 액티비티가 종료될 때 주기적 갱신 작업 중단
        stopDataRefreshTask()
    }
}