package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import android.os.Build
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
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.navercorp.nid.oauth.NidOAuthPreferencesManager.state
import com.test.keepgardeningproject_seller.DAO.auctionInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.MY_PAGE_SELLER_AUCTION_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.AuctionSellerDetailRepository
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerAuctionBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerAuctionBinding
import java.lang.RuntimeException
import java.net.URL
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


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
        viewModel = ViewModelProvider(mainActivity)[MyPageSellerAuctionViewModel::class.java]

        viewModel.run {
            sellerList.observe(mainActivity){
                myPageSellerAuctionBinding.recyclerviewAs.adapter?.notifyDataSetChanged()
            }
        }
        myPageSellerAuctionBinding.run {
            //getData()
            recyclerviewAs.run {
                adapter = ResultrecyclerAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context,MaterialDividerItemDecoration.VERTICAL))
            }
            toolbarAs.run {
                setTitle("경매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    viewModel.resetList()
                    mainActivity.removeFragment(MY_PAGE_SELLER_AUCTION_FRAGMENT)
                }
            }
            var myidx =  mainActivity.loginSellerInfo.userSellerIdx
            viewModel.getPostALl(myidx)
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

                rowbinding.root.setOnClickListener {

                    var bundle = Bundle()
                    var productidx = viewModel.sellerList.value?.get(adapterPosition)!!.auctionProductIdx
                    bundle.putLong("productidx",productidx)
                    mainActivity.replaceFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT,true,bundle)
                }
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
            return viewModel.sellerList.value?.size!!


        }

        override fun onBindViewHolder(holder: ResultrecyclerAdapter.viewholderclass, position: Int) {

            holder.textViewAuctionState.text = viewModel.sellerList.value?.get(position)?.auctionDetailState.toString()
            holder.textViewAuctionName.text =viewModel.sellerList.value?.get(position)?.auctionDetailTitle.toString()
            AuctionSellerDetailRepository.getImage(viewModel.sellerList.value?.get(position)?.auctionDetailImg.toString()){
                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.imageviewAuctionimg)

            }


        }
    }





    fun getData(){

            var idx = mainActivity.loginSellerInfo.userSellerIdx
            Log.d("Lim","${idx}")

            AuctionSellerDetailRepository.getAuctionSellerDetailIdx {
                var idx2 = it.result.value as Long
                idx2++
                Log.d("Lim","${idx2}")
                AuctionProductRepository.getAuctionProductDetailInfoByIdx(idx){
                    for(c1 in it.result.children){
                        var newname = c1.child("auctionProductName").value.toString()
                        var newimg = c1.child("auctionProductImageList").value as ArrayList<String>
                        var productIdx = c1.child("auctionProductIdx").value as Long
                        var imgone = newimg[0]
                        var state = c1.child("auctionProductCloseDate").value.toString()
                        var newstate = getTime(state)
                        var newclass = auctionInfo(idx,imgone,newname,newstate,productIdx,idx2)

                        AuctionSellerDetailRepository.setAuctionSellerDetailInfo(newclass){
                            AuctionSellerDetailRepository.setAuctioSellerDetailIdx(idx2){
                                Log.d("Lim","${newclass}")
                            }
                        }
                    }

                }
            }

    }


    fun getTime(state:String):String{
        var date: Date = Calendar.getInstance().time
        date = SimpleDateFormat("yyyy/MM/dd HH:mm") .parse(state)
        //현재시간
        var today = Calendar.getInstance()
        //경매종료시간 -현재시간
        var calculateDate = (date.time - today.time.time)

//        var calculateDay = calculateDate/(24 * 60 * 60 * 1000)
//        var calculateHour = (calculateDate/(60 * 60 * 1000)) - calculateDay*24
//        var calculateMinute = (calculateDate/(60 * 1000)) - calculateHour*60 - calculateDay*24*60
//
//        fragmentAuctionSellerMainBinding.textViewAuctionSellerMainTimeValue.text = "${calculateDay}일 ${calculateHour}시 ${calculateMinute}분"

        if(calculateDate < 0) {
            val state  = "경매완료"
            return state
        } else {
            val state  = "경매중"
            return state
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetList()
    }

}

