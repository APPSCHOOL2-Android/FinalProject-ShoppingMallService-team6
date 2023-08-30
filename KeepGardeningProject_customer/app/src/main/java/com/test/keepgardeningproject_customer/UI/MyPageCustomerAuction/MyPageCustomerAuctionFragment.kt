package com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets.Side.all
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.DAO.purchaseInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase.MyPageCustomerPurchaseFragment
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseButtonBinding
import java.lang.RuntimeException
import java.net.URL
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
            mypagecustomerauctionviewModel.getAll()
            recyclerviewAc.run {
                adapter = AuctionRecyclerViewAdapter()
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
                    mypagecustomerauctionviewModel.resetList()
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_AUCTION_FRAGMENT)
                }

            }

        }

        return fragmentAuctionCustomerBinding.root
    }

    inner class AuctionRecyclerViewAdapter : RecyclerView.Adapter<AuctionRecyclerViewAdapter.allViewHolder>(){
        inner class allViewHolder(rowPostListBinding: RowMyPageCustomerAuctionBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListState:TextView
            val rowPostListNickName:TextView
            val imageviewPtimg :ImageView
            val rowPostListPrice:TextView
            init{
                rowPostListState = rowPostListBinding.textviewAcState
                rowPostListNickName = rowPostListBinding.textViewAcProductName
                imageviewPtimg = rowPostListBinding.imageviewAcImg1
                rowPostListPrice = rowPostListBinding.textViewAcPrices


                rowPostListBinding.root.setOnClickListener {
                    var productidx = mypagecustomerauctionviewModel.getauctiondetailList.value!!.get(adapterPosition).productidx
                    MainActivity.chosedAuctionProductIdx = productidx
                    mainActivity.replaceFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT,true,null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): allViewHolder {
            val rowPostListBinding =RowMyPageCustomerAuctionBinding.inflate(layoutInflater)
            val allViewHolder = allViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
          return mypagecustomerauctionviewModel.getauctiondetailList.value?.size!!
        }

        override fun onBindViewHolder(holder: allViewHolder, position: Int) {
            holder.rowPostListNickName.text = mypagecustomerauctionviewModel.getauctiondetailList.value!!.get(position).name
            holder.rowPostListState.text = mypagecustomerauctionviewModel.getauctiondetailList.value!!.get(position).state
            holder.rowPostListPrice.text = mypagecustomerauctionviewModel.getauctiondetailList.value!!.get(position).price + " 원"
            PurchaseRepository.getImage(mypagecustomerauctionviewModel.getauctiondetailList.value!!.get(position)?.img.toString()){

                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.imageviewPtimg)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mypagecustomerauctionviewModel.resetList()
    }


}


