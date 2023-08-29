package com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerAuctionBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

class MyPageCustomerAuctionFragment : Fragment() {

    lateinit var fragmentAuctionCustomerBinding: FragmentMyPageCustomerAuctionBinding
    lateinit var mainActivity: MainActivity
    lateinit var mypagecustomerauctionviewModel: MyPageCustomerAuctionViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionCustomerBinding =
            FragmentMyPageCustomerAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        mypagecustomerauctionviewModel = ViewModelProvider(mainActivity)[MyPageCustomerAuctionViewModel::class.java]

        mypagecustomerauctionviewModel.run {
           getauctiondetailList.observe(mainActivity) {
               fragmentAuctionCustomerBinding.recyclerviewAc.adapter?.notifyDataSetChanged()
           }
        }

        fragmentAuctionCustomerBinding.run {
            mypagecustomerauctionviewModel.getData()
            recyclerviewAc.run {
                adapter = ResultRecyclerviewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    MaterialDividerItemDecoration(
                        context,
                        MaterialDividerItemDecoration.VERTICAL
                    )
                )
            }

            toolbarAc.run {
                setTitle("경매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    //마이페이지 메인화면으로 이동
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_AUCTION_FRAGMENT)
                }

            }

        }

        return fragmentAuctionCustomerBinding.root
    }

    inner class ResultRecyclerviewAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }


    }


}







//var newformatter = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
//
////현재시간 가져오기
//var now = System.currentTimeMillis()
//
//
//AuctionProductRepository.getAuctionProductIndex {
//    val idx = it.result.value as Long
//    Log.d("lim","${idx}")
//    AuctionProductRepository.getAuctionProductInfo(idx) {
//        for(c1 in it.result.children){
//            //이름,상태,이미지
//            var newname = c1.child("auctionProductName").value.toString()
//            var newdate = c1.child("auctionProductCloseDate").value.toString()
//            var newimg = c1.child("auctionProductImageList").value.toString()
//
//            Log.d("Lim","${newname}")
//            Log.d("Lim","${newdate}")
//            Log.d("Lim","${newimg}")
//
//
//        }
//    }
//}
data class auctionCustomerDetail(var name:String,var img:String,var state:String)
