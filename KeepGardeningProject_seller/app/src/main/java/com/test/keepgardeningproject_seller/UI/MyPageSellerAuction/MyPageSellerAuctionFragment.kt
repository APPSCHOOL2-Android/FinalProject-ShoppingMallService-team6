package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.MY_PAGE_SELLER_AUCTION_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerAuctionBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerAuctionBinding
import java.lang.RuntimeException


//가짜데이터 모델
data class Models(val type:Int,val img:Int,val state:String,val title:String){
    companion object{
        const val AUCTION_COMPLETE_TYPE = 0
        const val AUCTION_PROCEED_TYPE = 1
    }
}
class MyPageSellerAuctionFragment : Fragment() {

    lateinit var myPageSellerAuctionBinding: FragmentMyPageSellerAuctionBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: MyPageSellerAuctionViewModel

    //가짜 데이터
    val datas  = listOf(
        Models(Models.AUCTION_PROCEED_TYPE,R.mipmap.ic_launcher,"경매중","몬스테라"),
        Models(Models.AUCTION_PROCEED_TYPE,R.mipmap.ic_launcher,"경매중","장미"),
        Models(Models.AUCTION_COMPLETE_TYPE,R.mipmap.ic_launcher,"경매완료","목련"),
        Models(Models.AUCTION_COMPLETE_TYPE,R.mipmap.ic_launcher,"경매완료","해바라기"),
        Models(Models.AUCTION_PROCEED_TYPE,R.mipmap.ic_launcher,"경매중","개나리")
    )
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
            val imageviewAuctionimg: ImageView

            init {
                textViewAuctionName = rowbinding.textViewAsProductName
                textViewAuctionState = rowbinding.textviewAsState
                imageviewAuctionimg = rowbinding.imageviewAsImg
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
            return datas.size
        }

        override fun onBindViewHolder(holder: ResultrecyclerAdapter.viewholderclass, position: Int) {

            holder.textViewAuctionState.text = datas[position].state
            holder.textViewAuctionName.text = datas[position].title
            holder.imageviewAuctionimg.setImageResource(R.mipmap.ic_launcher)

            //경매중인 아이템뷰들만 경매상세정보로 이동
            if(datas[position].type == 1){
                holder.itemView.setOnClickListener{
                    mainActivity.replaceFragment(MainActivity.AUCTION_SELLER_DETAIL_FRAGMENT,true,null)
                }
            }

        }
    }



}