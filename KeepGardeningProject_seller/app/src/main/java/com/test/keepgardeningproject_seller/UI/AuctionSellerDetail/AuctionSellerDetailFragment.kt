package com.test.keepgardeningproject_seller.UI.AuctionSellerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainFragment.Companion.auctionProductIdx
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainViewModel
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerDetailBinding
import com.test.keepgardeningproject_seller.databinding.RowAuctionSellerDetailBinding

class AuctionSellerDetailFragment : Fragment() {

    lateinit var fragmentAuctionSellerDetailBinding: FragmentAuctionSellerDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var auctionSellerMainViewModel: AuctionSellerMainViewModel

    var fileNameList = mutableListOf<String>()

    companion object {
        fun newInstance() = AuctionSellerDetailFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerDetailBinding = FragmentAuctionSellerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        auctionSellerMainViewModel = ViewModelProvider(mainActivity)[AuctionSellerMainViewModel::class.java]
        auctionSellerMainViewModel.run {

            auctionProductDetail.observe(mainActivity) {
                fragmentAuctionSellerDetailBinding.textViewAuctionSellerDetailProductContent.text = it
            }
            auctionProductImageNameList.observe(mainActivity) {
                fileNameList = it
                fragmentAuctionSellerDetailBinding.recyclerViewAuctionSellerDetail.adapter?.notifyDataSetChanged()
            }
            auctionSellerMainViewModel.getAuctionProductInfo(auctionProductIdx.toLong())
        }

        fragmentAuctionSellerDetailBinding.run {
            recyclerViewAuctionSellerDetail.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
            }

        }
        return fragmentAuctionSellerDetailBinding.root
    }

    override fun onResume() {
        super.onResume()

        fragmentAuctionSellerDetailBinding.root.requestLayout()

        var adapter = fragmentAuctionSellerDetailBinding.recyclerViewAuctionSellerDetail.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowAuctionSellerDetailBinding: RowAuctionSellerDetailBinding) : RecyclerView.ViewHolder(rowAuctionSellerDetailBinding.root) {

            var imageViewProductDetail : ImageView

            init {
                imageViewProductDetail = rowAuctionSellerDetailBinding.imageViewRowAuctionSellerDetail
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowAuctionSellerDetailBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return fileNameList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

            var fileName = auctionSellerMainViewModel.auctionProductImageNameList.value?.get(position)!!
            AuctionProductRepository.getAuctionProductImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewProductDetail)
            }
        }
    }

}