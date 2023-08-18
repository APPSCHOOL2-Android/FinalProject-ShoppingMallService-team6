package com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase.MyPageCustomerPurchaseFragment
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerBinding
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerAuctionBinding

class MyPageCustomerAuctionFragment : Fragment() {

    lateinit var fragmentAuctionCustomerBinding: FragmentMyPageCustomerAuctionBinding
    lateinit var mainActivity: MainActivity
    lateinit var mypagecustomerauctionviewModel: MyPageCustomerAuctionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       //return inflater.inflate(R.layout.fragment_my_page_customer_auction, container, false)
        fragmentAuctionCustomerBinding = FragmentMyPageCustomerAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentAuctionCustomerBinding.run {

            recyclerviewAc.run {
                adapter = ResultRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

            toolbarAc.run {
                setTitle("경매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_AUCTION_FRAGMENT)
                }

            }


        }

        return  fragmentAuctionCustomerBinding.root
    }
    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(rowAuctionListBinding: RowMyPageCustomerAuctionBinding) : RecyclerView.ViewHolder(rowAuctionListBinding.root){

            val textViewAuctionState:TextView
            val textViewAuctionName:TextView
            val buttonAuctionBuy: Button

            init{
                textViewAuctionName = rowAuctionListBinding.textViewAcProductName
                textViewAuctionState = rowAuctionListBinding.textviewAcState
                buttonAuctionBuy = rowAuctionListBinding.buttonAcBuy
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowMyPageCustomerAuctionBinding = RowMyPageCustomerAuctionBinding.inflate(layoutInflater)
            val allViewHolder = ResultViewHolder(rowMyPageCustomerAuctionBinding)

            rowMyPageCustomerAuctionBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.textViewAuctionState.text  = "[입찰완료]"
            holder.textViewAuctionName.text =  "몬스테라"
            holder.buttonAuctionBuy.visibility = View.GONE
            if(position/2 ==0){
                holder.buttonAuctionBuy.visibility = View.VISIBLE
            }
        }
    }

}