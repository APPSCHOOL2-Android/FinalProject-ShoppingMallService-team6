package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowAuctionCustomerDetailAuctionBinding


class AuctionCustomerDetailAuctionFragment : Fragment() {

    lateinit var auctionCustomerDetailAuctionBinding: FragmentAuctionCustomerDetailAuctionBinding
    lateinit var mainactivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       auctionCustomerDetailAuctionBinding = FragmentAuctionCustomerDetailAuctionBinding.inflate(layoutInflater)
        mainactivity = activity as MainActivity
        auctionCustomerDetailAuctionBinding.run {
            recyclerviewAcDetailAuction.run {
                adapter = recyclerviewAdaper()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    MaterialDividerItemDecoration(context,
                        MaterialDividerItemDecoration.VERTICAL)
                )
            }
        }

        return auctionCustomerDetailAuctionBinding.root
    }

    inner class recyclerviewAdaper: RecyclerView.Adapter<recyclerviewAdaper.viewholderclass>(){
        inner class viewholderclass(rowbinding: RowAuctionCustomerDetailAuctionBinding): RecyclerView.ViewHolder(rowbinding.root){
           var auctionNum:TextView
           var auctionNicknames:TextView
           var auctionOrderdate:TextView
           var auctionOrderPrice:TextView
           init {
               auctionNum = rowbinding.textViewRowAcdAuctionNum
               auctionNicknames = rowbinding.textviewRowAcdAuctionName
               auctionOrderdate = rowbinding.textviewRowAcdAuctionDate
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
            return 2
        }

        override fun onBindViewHolder(holder: viewholderclass, position: Int) {
            holder.auctionOrderPrice.text = "${position}"
            holder.auctionOrderdate.text = "${position}"
            holder.auctionNicknames.text = "JAMES"
            holder.auctionNum.text = "${position}"
        }
    }

    //tab 전환시 layout를 다시 요청
    override fun onResume() {
        super.onResume()
        auctionCustomerDetailAuctionBinding.root.requestLayout()


    }

}