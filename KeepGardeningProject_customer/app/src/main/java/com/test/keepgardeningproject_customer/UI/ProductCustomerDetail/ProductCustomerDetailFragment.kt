package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository.Companion.getProductSellerInfoByIdx
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailBottomDialog
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailDetailFragment
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components.ProductCustomerDetailReviewFragment
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailBinding
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailDetailBinding
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailReviewBinding
import java.text.DecimalFormat

class ProductCustomerDetailFragment : Fragment() {

    lateinit var fragmentProductCustomerDetailBinding: FragmentProductCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    // 뷰페이저 프래그먼트
    lateinit var productCustomerDetailDetailFragment: ProductCustomerDetailDetailFragment
    lateinit var productCustomerDetailReviewFragment: ProductCustomerDetailReviewFragment

    // 뷰모델
    lateinit var productCustomerDetailViewModel: ProductCustomerDetailViewModel
    var idx : Long = 1
    lateinit var price : String

    // 탭
    val tabName = arrayOf(
        "상세정보", "리뷰"
    )

    val fragmentList = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductCustomerDetailBinding =
            FragmentProductCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        productCustomerDetailDetailFragment = ProductCustomerDetailDetailFragment()
        productCustomerDetailReviewFragment = ProductCustomerDetailReviewFragment()

        productCustomerDetailViewModel =
            ViewModelProvider(mainActivity)[ProductCustomerDetailViewModel::class.java]

        productCustomerDetailViewModel.run {
            var mainbinding = fragmentProductCustomerDetailBinding
            productInfo.observe(mainActivity) {

                // 카테고리 설정
                mainbinding.textViewPcdCategory.text = "카테고리 > " + it.productCategory

                // 이미지뷰 설정
                var fileNameList = it.productImageList
                var fileName = fileNameList?.get(0)!!
                ProductRepository.getProductImage(fileName) {
                    val fileUri = it.result
                    Glide.with(mainActivity).load(fileUri).into(mainbinding.imagePcd)
                }

                // 스토어명
                this.getUserSellerInfoByStoreIdx(it.productStoreIdx!!.toDouble())

                // 상품명
                mainbinding.textViewPcdTitle.text = it.productName

                // 가격
                val dec = DecimalFormat("#,###")
                val temp = dec.format(it.productPrice!!.toInt())
                mainbinding.textViewPcdPrice.text = temp + " 원"
                price = it.productPrice!!
            }

            userSellerInfo.observe(mainActivity) {
                // 스토어명
                mainbinding.textViewPcdStore.text = it.userSellerStoreName
            }
        }

        // 받아온 상품 인덱스
        idx = arguments?.getLong("selectedProductIdx", 1)!!

        // 뷰모델 함수 실행
        productCustomerDetailViewModel.getProductInfoByIdx(idx.toDouble())

        fragmentProductCustomerDetailBinding.run {
            // 툴바
            toolbarPcd.run {
                title = "상품상세정보"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT)
                }
            }

            // 구매하기 버튼 바텀 다이얼로그
            buttonPcdBuy.setOnClickListener {
                val bs = ProductCustomerDetailBottomDialog()
                bs.show(mainActivity.supportFragmentManager, "구매")
            }

            buttonPcdQna.setOnClickListener {
                if(!MainActivity.isLogined) {
                    mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT,true,null)
                } else {
                    // 로그인이 이미 된경우
                    val newBundle = Bundle()
                    newBundle.putLong("productIdx", idx)
                    mainActivity.replaceFragment(PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT, true, newBundle)
                }
            }


            // 탭레이아웃
            fragmentList.add(productCustomerDetailDetailFragment)
            fragmentList.add(productCustomerDetailReviewFragment)

            viewPagerPcdTab.adapter = TabAdapterClass(mainActivity)
            val tabLayoutMediator =
                TabLayoutMediator(tabsPcd, viewPagerPcdTab) { tab: TabLayout.Tab, i: Int ->
                    tab.text = tabName[i]
                }
            tabLayoutMediator.attach()

        }

        return fragmentProductCustomerDetailBinding.root
    }

    //tab 전환시 layout를 다시 요청
    override fun onResume() {
        super.onResume()
        fragmentProductCustomerDetailBinding.run {
            viewPagerPcdTab.requestLayout()
        }
    }


    inner class TabAdapterClass(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}