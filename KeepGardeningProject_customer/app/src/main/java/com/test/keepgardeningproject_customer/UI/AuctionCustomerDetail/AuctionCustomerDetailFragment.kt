package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_customer.DAO.AuctionInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailFragment
import com.test.keepgardeningproject_customer.databinding.DialogAcdBinding

import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailBinding
import java.nio.file.attribute.AclEntry.Builder
import java.text.DecimalFormat

class AuctionCustomerDetailFragment : Fragment() {

    lateinit var auctionCustomerDetailBinding: FragmentAuctionCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    // 표시할 Fragment들을 담을 리스트
    val fragmentList = mutableListOf<Fragment>()

    // 뷰모델
    lateinit var viewModel: AuctionCustomerDetailViewModel
    // 선택한 인덱스
    var idx: Long = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auctionCustomerDetailBinding = FragmentAuctionCustomerDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[AuctionCustomerDetailViewModel::class.java]

        viewModel.run{
            var binding = auctionCustomerDetailBinding

            auctionProductInfo.observe(mainActivity){
                // 대표이미지 설정 (한장)
                var fileNameList = it.auctionProductImageList
                var fileName = fileNameList?.get(0)!!
                ProductRepository.getProductImage(fileName){
                    val fileUri = it.result
                    Glide.with(mainActivity).load(fileUri).into(binding.imageviewAcdTitle)
                }

                // 스토어명
                this.getUserSellerInfoByStoreIdx(it.auctionProductStoreIdx!!.toDouble())

                // 상품명
                binding.textviewAcdTitle.text = it.auctionProductName

                // 시작가격
                val dec = DecimalFormat("#,###")
                val temp = dec.format(it.auctionProductOpenPrice!!.toInt())
                binding.textviewAcdOpenprice.text = temp + " 원"

                // 현재가격
                val temp2 = dec.format(it.auctionProductOpenPrice!!.toInt())
                binding.textviewAcdCurrentprice.text = temp2 + " 원"

                // 경매상태 : 경매가능

                // 경매기간
                binding.textviewAcdPeriodnow.text = it.auctionProductOpenDate + " ~ " + it.auctionProductCloseDate
            }

            userSellerInfo.observe(mainActivity){
                // 스토어명
                binding.textviewAcdStore.text = it.userSellerStoreName
            }
        }

        // 받아온 경매상품 인덱스
        idx = arguments?.getLong("selectedAuctionProductIdx", 1)!!
        viewModel.getAPByIdx(idx.toDouble())

        auctionCustomerDetailBinding.run {

            //탭 이름
            val tabName = arrayOf(
                "상세정보", "경매정보"
            )

            // 탭레이아웃
            fragmentList.clear()
            fragmentList.add(AuctionCustomerDetailInfoFragment())
            fragmentList.add(AuctionCustomerDetailAuctionFragment())
            pager2.adapter = TabsAdapterClass(mainActivity)
            val tabLayoutMediator = TabLayoutMediator(tabs,pager2){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()

            // 툴바
            toolbarAcDetail.run {
                setTitle("상품상세정보")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT)
                }
            }
            
            // 입찰하기 버튼
            buttonAcdInsert.run {
                setOnClickListener {
                    // 로그인 안되어있으면
                    if(MainActivity.isLogined == false){
                        mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT,true,null)
                    }
                    // 로그인 되어있으면
                    else{

                        val dialogAcdBinding = DialogAcdBinding.inflate(layoutInflater)
                        val dialogBuilder = AlertDialog.Builder(mainActivity)
                        dialogBuilder.setTitle("입찰금액")
                        dialogBuilder.setView(dialogAcdBinding.root)
                        dialogAcdBinding.editTextTextDialogAcdPrice.requestFocus()
                        // 입찰하기 버튼
                        dialogBuilder.setPositiveButton("확인", ){ dialogInterface: DialogInterface, i: Int ->
                            var auctionPrice = dialogAcdBinding.editTextTextDialogAcdPrice.text.toString()

                            AuctionRepository.getAuctionIdx {
                                // 현재의 사용자 순서값 가져온다.
                                var auctionIndex = it.result.value as Long

                                // 저장할 데이터 담기
                                var auctionAuctionProductIndex = idx
                                var auctionState = "입찰완료"
                                var auctionCustomerList = ArrayList<Long>()

                                auctionCustomerList.add(MainActivity.loginedUserInfo.userIdx!!)

                                var auctionInfo = AuctionInfo(auctionIndex, auctionAuctionProductIndex, auctionState, auctionCustomerList)

                                AuctionRepository.setAuctionProduct(auctionInfo){
                                    AuctionRepository.setAuctionIndex(auctionIndex){
                                        Snackbar.make(auctionCustomerDetailBinding.root, "입찰등록이 완료되었습니다", Snackbar.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        dialogBuilder.setNegativeButton("취소",null)
                        dialogBuilder.show()
                    }
                }
            }
            
            // 문의하기 버튼
            buttonAcdQnas.run {
                setOnClickListener {
                    //문의작성으로 이동
                }
            }

        }
        return auctionCustomerDetailBinding.root
    }

    inner class TabsAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    //페이저 자체를 레이아웃을 lifecycle 처음부터 다시 그리기
    override fun onResume() {
        super.onResume()
        auctionCustomerDetailBinding.pager2.requestLayout()
    }



}