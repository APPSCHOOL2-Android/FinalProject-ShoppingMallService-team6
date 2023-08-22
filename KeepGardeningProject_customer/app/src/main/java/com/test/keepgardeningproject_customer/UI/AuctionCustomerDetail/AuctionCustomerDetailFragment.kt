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

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailFragment
import com.test.keepgardeningproject_customer.databinding.DialogAcdBinding

import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailBinding
import java.nio.file.attribute.AclEntry.Builder

class AuctionCustomerDetailFragment : Fragment() {

    lateinit var auctionCustomerDetailBinding: FragmentAuctionCustomerDetailBinding
    private lateinit var viewModel: AuctionCustomerDetailViewModel
    lateinit var mainActivity: MainActivity

    //탭 이름
    val tabName = arrayOf(
        "상세정보", "경매정보"
    )
    // 표시할 Fragment들을 담을 리스트
    val fragmentList = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auctionCustomerDetailBinding = FragmentAuctionCustomerDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        auctionCustomerDetailBinding.run {

            fragmentList.add(AuctionCustomerDetailInfoFragment())
            fragmentList.add(AuctionCustomerDetailAuctionFragment())

            pager2.adapter = TabsAdapterClass(mainActivity)


            val tabLayoutMediator = TabLayoutMediator(tabs,pager2){ tab: TabLayout.Tab, i: Int ->
                tab.text = tabName[i]
            }
            tabLayoutMediator.attach()

            toolbarAcDetail.run {
                setTitle("상품상세정보")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT)
                }
            }
            buttonAcdInsert.run {
                setOnClickListener {
                    val dialogAcdBinding = DialogAcdBinding.inflate(layoutInflater)
                    val dialogBuilder = AlertDialog.Builder(mainActivity)
                    dialogBuilder.setTitle("입찰금액")
                    dialogBuilder.setView(dialogAcdBinding.root)
                    dialogAcdBinding.editTextTextDialogAcdPrice.requestFocus()
                    dialogBuilder.setPositiveButton("확인", ){ dialogInterface: DialogInterface, i: Int ->

                    }
                    dialogBuilder.setNegativeButton("취소",null)
                    dialogBuilder.show()
                }
            }
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