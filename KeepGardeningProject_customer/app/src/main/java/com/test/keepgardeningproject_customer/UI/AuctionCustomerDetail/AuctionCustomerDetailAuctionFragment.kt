package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.DAO.AuctionInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowAuctionCustomerDetailAuctionBinding


class AuctionCustomerDetailAuctionFragment : Fragment() {

    lateinit var auctionCustomerDetailAuctionBinding: FragmentAuctionCustomerDetailAuctionBinding
    lateinit var mainActivity: MainActivity
    lateinit var auctionCustomerDetailFragment: AuctionCustomerDetailFragment

    lateinit var viewModel: AuctionCustomerDetailViewModel

    // 전체 aal
    var aal = mutableListOf<AuctionInfo>()

    // 해당 상품의 al
    var cal = mutableListOf<AuctionInfo>()

    var idx : Long = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auctionCustomerDetailAuctionBinding =
            FragmentAuctionCustomerDetailAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        auctionCustomerDetailFragment = AuctionCustomerDetailFragment()

        idx = arguments?.getLong("idx", 1)!!

        viewModel = ViewModelProvider(mainActivity)[AuctionCustomerDetailViewModel::class.java]
        viewModel.run{
            auctionList.observe(mainActivity){
                aal = it
                for(i in aal){
                    Log.d("%%%%%",idx.toString())
                    if(i.auctionAuctionProductIndex == idx){
                        cal.add(i)
                        auctionCustomerDetailAuctionBinding.recyclerviewAcDetailAuction.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        auctionCustomerDetailAuctionBinding.run {
            recyclerviewAcDetailAuction.run {
                adapter = recyclerviewAdaper()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    MaterialDividerItemDecoration(
                        context,
                        MaterialDividerItemDecoration.VERTICAL
                    )
                )
            }
        }

        return auctionCustomerDetailAuctionBinding.root
    }

    inner class recyclerviewAdaper : RecyclerView.Adapter<recyclerviewAdaper.viewholderclass>() {
        inner class viewholderclass(rowbinding: RowAuctionCustomerDetailAuctionBinding) :
            RecyclerView.ViewHolder(rowbinding.root) {
            var auctionNum: TextView
            var auctionNicknames: TextView
            var auctionOrderPrice: TextView

            init {
                auctionNum = rowbinding.textViewRowAcdAuctionNum
                auctionNicknames = rowbinding.textviewRowAcdAuctionName
                auctionOrderPrice = rowbinding.textviewRowAcdAuctionPrice
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholderclass {
            val rowbinding = RowAuctionCustomerDetailAuctionBinding.inflate(layoutInflater)
            val viewholderclass = viewholderclass(rowbinding)

            rowbinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return viewholderclass
        }

        override fun getItemCount(): Int {
            return cal.size
        }

        override fun onBindViewHolder(holder: viewholderclass, position: Int) {
            holder.auctionOrderPrice.text = cal[position].auctionBidPrice!!
            holder.auctionNicknames.text = cal[position].auctionBidNickname!!
            holder.auctionNum.text = "${position}"
        }
    }

    //tab 전환시 layout를 다시 요청
    override fun onResume() {
        super.onResume()
        auctionCustomerDetailAuctionBinding.root.requestLayout()
    }

}