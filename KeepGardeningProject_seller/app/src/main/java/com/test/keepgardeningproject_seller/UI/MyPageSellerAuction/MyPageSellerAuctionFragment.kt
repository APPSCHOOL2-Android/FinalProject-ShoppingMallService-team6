package com.test.keepgardeningproject_seller.UI.MyPageSellerAuction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.MY_PAGE_SELLER_AUCTION_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionSellerDetailRepository
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerAuctionBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerAuctionBinding
import java.net.URL


class MyPageSellerAuctionFragment : Fragment() {

    lateinit var myPageSellerAuctionBinding: FragmentMyPageSellerAuctionBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: MyPageSellerAuctionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        myPageSellerAuctionBinding = FragmentMyPageSellerAuctionBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[MyPageSellerAuctionViewModel::class.java]

        viewModel.run {
            sellerList.observe(mainActivity) {
                myPageSellerAuctionBinding.recyclerviewAs.adapter?.notifyDataSetChanged()
            }
        }
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
                    viewModel.resetList()
                    mainActivity.removeFragment(MY_PAGE_SELLER_AUCTION_FRAGMENT)
                }
            }
            var useridx = mainActivity.loginSellerInfo.userSellerIdx
            viewModel.getData(useridx)
        }
        return myPageSellerAuctionBinding.root
    }

    inner class ResultrecyclerAdapter :
        RecyclerView.Adapter<ResultrecyclerAdapter.viewholderclass>() {
        inner class viewholderclass(rowbinding: RowMyPageSellerAuctionBinding) :
            RecyclerView.ViewHolder(rowbinding.root) {

            val textViewAuctionState: TextView
            val textViewAuctionName: TextView
            val imageviewAuctionimg: ImageView

            init {
                textViewAuctionName = rowbinding.textViewAsProductName
                textViewAuctionState = rowbinding.textviewAsState
                imageviewAuctionimg = rowbinding.imageviewAsImg

                rowbinding.root.setOnClickListener {
                    var bundle = Bundle()
                    var productidx =
                        viewModel.sellerList.value?.get(adapterPosition)!!.auctionProductIdx.toInt()
                    bundle.putInt("auctionProductIdx", productidx)
                    mainActivity.replaceFragment(
                        MainActivity.AUCTION_SELLER_MAIN_FRAGMENT,
                        true,
                        bundle
                    )
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholderclass {

            var rowMyPageSellerAuctionBinding =
                RowMyPageSellerAuctionBinding.inflate(layoutInflater)
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

        override fun onBindViewHolder(
            holder: ResultrecyclerAdapter.viewholderclass,
            position: Int,
        ) {

            holder.textViewAuctionState.text =
                viewModel.sellerList.value?.get(position)?.auctionDetailState.toString()
            holder.textViewAuctionName.text =
                viewModel.sellerList.value?.get(position)?.auctionDetailTitle.toString()
            AuctionSellerDetailRepository.getImage(viewModel.sellerList.value?.get(position)?.auctionDetailImg.toString()) {
                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200, 200)
                    .into(holder.imageviewAuctionimg)

            }


        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetList()
    }



}







