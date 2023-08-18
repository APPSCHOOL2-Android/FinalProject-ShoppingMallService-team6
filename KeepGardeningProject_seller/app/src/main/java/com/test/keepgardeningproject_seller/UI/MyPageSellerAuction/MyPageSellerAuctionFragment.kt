package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.MY_PAGE_SELLER_AUCTION_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerAuctionBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerAuctionBinding

class MyPageSellerAuctionFragment : Fragment() {

    lateinit var myPageSellerAuctionBinding: FragmentMyPageSellerAuctionBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: MyPageSellerAuctionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageSellerAuctionBinding = FragmentMyPageSellerAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        myPageSellerAuctionBinding.run {
            recyclerviewAs.run {
                adapter = ResultrecyclerAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context,MaterialDividerItemDecoration.VERTICAL))
            }
            toolbarAs.run {
                setTitle("경매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MY_PAGE_SELLER_AUCTION_FRAGMENT)
                }
            }
        }
        return myPageSellerAuctionBinding.root
    }

    inner class ResultrecyclerAdapter:RecyclerView.Adapter<ResultrecyclerAdapter.viewholderclass>(){
        inner class viewholderclass(rowbinding:RowMyPageSellerAuctionBinding):RecyclerView.ViewHolder(rowbinding.root){
            val textViewAuctionState: TextView
            val textViewAuctionName: TextView

            init {

                textViewAuctionName = rowbinding.textViewAsProductName
                textViewAuctionState = rowbinding.textviewAsState
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholderclass {
            var rowMyPageSellerAuctionBinding = RowMyPageSellerAuctionBinding.inflate(layoutInflater)
            val allViewholder = viewholderclass(rowMyPageSellerAuctionBinding)

            rowMyPageSellerAuctionBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return allViewholder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: viewholderclass, position: Int) {

            holder.textViewAuctionName.text = "몬스테라"
            holder.textViewAuctionState.text = "[경매완료]"
        }
    }



}