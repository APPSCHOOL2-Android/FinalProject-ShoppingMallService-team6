package com.test.keepgardeningproject_seller.UI.HomeSeller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_seller.DAO.AuctionProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerAuctionBinding
import com.test.keepgardeningproject_seller.databinding.RowHomeSellerBinding
import java.text.DecimalFormat

class HomeSellerAuctionFragment : Fragment() {

    lateinit var fragmentHomeSellerAuctionBinding: FragmentHomeSellerAuctionBinding
    lateinit var mainActivity: MainActivity

    lateinit var homeSellerViewModel: HomeSellerViewModel

    var auctionProductList = mutableListOf<AuctionProductClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeSellerAuctionBinding = FragmentHomeSellerAuctionBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        homeSellerViewModel = ViewModelProvider(mainActivity)[HomeSellerViewModel::class.java]
        homeSellerViewModel.run{
            auctionProductClassList.observe(mainActivity){
                auctionProductList = it
                fragmentHomeSellerAuctionBinding.recyclerViewHomeSellerAuction.adapter?.notifyDataSetChanged()
            }
            auctionProductImageNameList.observe(mainActivity){
                fragmentHomeSellerAuctionBinding.recyclerViewHomeSellerAuction.adapter?.notifyDataSetChanged()
            }
        }
        homeSellerViewModel.getAuctionProductInfoAll(mainActivity.loginSellerInfo.userSellerIdx)

        fragmentHomeSellerAuctionBinding.run {
            recyclerViewHomeSellerAuction.run {
                adapter = RecyclerAdapterClass()

                layoutManager = GridLayoutManager(mainActivity,3)
            }
        }

        return fragmentHomeSellerAuctionBinding.root
    }

    override fun onResume() {
        super.onResume()
        homeSellerViewModel.getAuctionProductInfoAll(mainActivity.loginSellerInfo.userSellerIdx)
        fragmentHomeSellerAuctionBinding.recyclerViewHomeSellerAuction.adapter?.notifyDataSetChanged()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowHomeSellerBinding: RowHomeSellerBinding) : RecyclerView.ViewHolder(rowHomeSellerBinding.root) {
            var imageViewRow : ImageView
            var textViewRowProductName : TextView
            var textViewRowStoreName : TextView
            var textViewRowPrice : TextView

            init {
                imageViewRow = rowHomeSellerBinding.imageViewRowHomeSeller
                textViewRowProductName = rowHomeSellerBinding.textViewRowHomeSellerProductName
                textViewRowStoreName = rowHomeSellerBinding.textViewRowHomeSellerStoreName
                textViewRowPrice = rowHomeSellerBinding.textViewRowHomeSellerProductPrice

                rowHomeSellerBinding.root.setOnClickListener {
                    val newBundle = Bundle()
                    newBundle.putString("oldFragment", "HomeSellerAuctionFragment")
                    newBundle.putInt("auctionProductIdx", auctionProductList[adapterPosition].auctionProductIdx.toInt())
                    mainActivity.replaceFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT,true,newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowHomeSellerBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return homeSellerViewModel.auctionProductClassList.value?.size!!
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            // 이미지 썸네일 넣기(대표 사진 0번)
            var fileName = homeSellerViewModel.auctionProductImageNameList.value?.get(position)!!
            AuctionProductRepository.getAuctionProductImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewRow)
            }

            // 상품명 표시
            holder.textViewRowProductName.text = homeSellerViewModel.auctionProductClassList.value?.get(position)?.auctionProductName

            // 상점명 표시
            holder.textViewRowStoreName.text = mainActivity.loginSellerInfo.userSellerStoreName


            // 숫자 comma 표시하기
            var decimal = DecimalFormat("#,###")
            var temp = homeSellerViewModel.auctionProductClassList.value?.get(position)?.auctionProductOpenPrice?.toInt()
            holder.textViewRowPrice.text = decimal.format(temp) + "원"
        }
    }
}