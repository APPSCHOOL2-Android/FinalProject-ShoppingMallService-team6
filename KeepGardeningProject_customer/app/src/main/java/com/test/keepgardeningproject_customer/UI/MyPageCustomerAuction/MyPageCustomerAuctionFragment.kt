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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.AuctionProductRepository
import com.test.keepgardeningproject_customer.Repository.AuctionRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerAuctionBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar

class MyPageCustomerAuctionFragment : Fragment() {

    lateinit var fragmentAuctionCustomerBinding: FragmentMyPageCustomerAuctionBinding
    lateinit var mainActivity: MainActivity
    lateinit var mypagecustomerauctionviewModel: MyPageCustomerAuctionViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionCustomerBinding =
            FragmentMyPageCustomerAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        val datas = listOf<Models>(
            Models(Models.AUCTION_TYPE,R.mipmap.ic_launcher,"[경매완료]","몬스테라",),
            Models(Models.BID_TYPE,R.mipmap.ic_launcher,"[입찰완료]","해바라기"),
            Models(Models.Auction_TYPE2,R.mipmap.ic_launcher,"[경매중]","목화",),
            Models(Models.AUCTION_TYPE,R.mipmap.ic_launcher,"[경매완료]","장미",),
            Models(Models.AUCTION_TYPE,R.mipmap.ic_launcher,"[경매완료]","목련",),
            Models(Models.BID_TYPE,R.mipmap.ic_launcher,"[입찰완료]","할미꽃",) ,
            Models(Models.Auction_TYPE2,R.mipmap.ic_launcher,"[경매중]","개나리",)
        )

        fragmentAuctionCustomerBinding.run {

            recyclerviewAc.run {
                adapter = ResultRecyclerviewAdapter(datas)
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


            //AuctionDA0 ->auctionState(상태), auctionCustomerList(useridx)
            //AuctionProduct -> auctionProductName(이름),auctionImageList(이미지)








            var newformatter = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")

            //현재시간 가져오기
            var now = System.currentTimeMillis()


            AuctionProductRepository.getAuctionProductIndex {
                val idx = it.result.value as Long
                Log.d("lim","${idx}")
                AuctionProductRepository.getAuctionProductInfo(idx) {
                    for(c1 in it.result.children){
                        //이름,상태,이미지
                        var newname = c1.child("auctionProductName").value.toString()
                        var newdate = c1.child("auctionProductCloseDate").value.toString()
                        var newimg = c1.child("auctionProductImageList").value.toString()

                        Log.d("Lim","${newname}")
                        Log.d("Lim","${newdate}")
                        Log.d("Lim","${newimg}")


                    }
                }
            }

        }

        return fragmentAuctionCustomerBinding.root
    }

    inner class ResultRecyclerviewAdapter(private val list:List<Models>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view :View?
            //결제완료 및 배송완료 분기처리
            return when(viewType){
                Models.AUCTION_TYPE->{
                    view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_page_customer_auction,parent,false)
                   AuctionTypeViewHolder(view)
                }
               Models.BID_TYPE->{
                    view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_page_customer_auction_button,parent,false)
                   BidTypeViewHolder(view)
                }
                Models.Auction_TYPE2->{
                    view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_page_customer_auction,parent,false)
                    AuctionTypeViewHolder(view)
                }
                else ->throw RuntimeException("뷰 타입 에러")
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val obj = list[position]
            when (obj.type) {
                Models.AUCTION_TYPE -> {
                    (holder as AuctionTypeViewHolder).txtAcState.text = obj.state
                    holder.txtAcName.text = obj.title
                    holder.imageviewAcimg.setImageResource(obj.img)
                    holder.itemView.setOnClickListener{
                        //경매상세정보로 이동
                        mainActivity.replaceFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT,true,null)
                    }

                }
                Models.BID_TYPE-> {
                    (holder as BidTypeViewHolder).txtBdState.text = obj.state
                    holder.txtBdName.text = obj.title
                    holder.imageviewBdimg.setImageResource(obj.img)
                    holder.butoonBdbuy.setText("구매하기")
                    holder.butoonBdbuy.setOnClickListener {
                        //구매하기로 이동
                        mainActivity.replaceFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT,true,null)
                    }
                }
                Models.Auction_TYPE2-> {
                    (holder as AuctionTypeViewHolder).txtAcState.text = obj.state
                    holder.txtAcName.text = obj.title
                    holder.imageviewAcimg.setImageResource(obj.img)
                    holder.itemView.setOnClickListener{

                        //경매상세정보로 이동
                        mainActivity.replaceFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT,true,null)
                    }

                }
            }
        }


        // 여기서 받는 position은 데이터의 index다.
        override fun getItemViewType(position: Int): Int {
            return list[position].type
        }

        //뷰홀더에 들어가 아이템 설정
        inner class AuctionTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtAcState: TextView = itemView.findViewById(R.id.textview_ac_state)
            val txtAcName :TextView = itemView.findViewById(R.id.textView_ac_productName)
            val imageviewAcimg :ImageView = itemView.findViewById(R.id.imageview_ac_img1)
        }

        inner class BidTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtBdState: TextView = itemView.findViewById(R.id.textview_ac_state2)
            val txtBdName :TextView = itemView.findViewById(R.id.textView_ac_productName2)
            val imageviewBdimg :ImageView = itemView.findViewById(R.id.imageview_ac_img2)
            val butoonBdbuy : Button = itemView.findViewById(R.id.button_ac_buy)
        }


    }


}
data class Models(var type:Int,var img:Int,var state:String,var title:String){
    companion object{
        val AUCTION_TYPE = 0
        val BID_TYPE = 1
        val Auction_TYPE2 = 2
    }
}

