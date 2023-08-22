package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailInfoBinding
import com.test.keepgardeningproject_customer.databinding.RowAuctionCustomerDetailInfoBinding


class AuctionCustomerDetailInfoFragment : Fragment() {

   lateinit var auctionCustomerDetailInfoBinding: FragmentAuctionCustomerDetailInfoBinding
   lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auctionCustomerDetailInfoBinding = FragmentAuctionCustomerDetailInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        auctionCustomerDetailInfoBinding.run {
            recyclerviewAcDetailInfo.run {
                adapter = recyclerviewAdaper()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context,MaterialDividerItemDecoration.VERTICAL))
            }
        }
        return auctionCustomerDetailInfoBinding.root
    }

    inner class recyclerviewAdaper:RecyclerView.Adapter<recyclerviewAdaper.viewholderclass>(){
        inner class viewholderclass(rowbinding:RowAuctionCustomerDetailInfoBinding):RecyclerView.ViewHolder(rowbinding.root){
            var auctiondetailinfoimg : ImageView
            init {

                auctiondetailinfoimg = rowbinding.imageviewRowAcdInfoImg
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholderclass {
           val rowbinding = RowAuctionCustomerDetailInfoBinding.inflate(layoutInflater)
            val viewholderclass = viewholderclass(rowbinding)

            rowbinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return viewholderclass
        }

        override fun getItemCount(): Int {
           return 50
        }

        override fun onBindViewHolder(holder: viewholderclass, position: Int) {
           holder.auctiondetailinfoimg.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun onResume() {
        super.onResume()
        auctionCustomerDetailInfoBinding.root.requestLayout()
    }

}