package com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerDetailInfoBinding
import com.test.keepgardeningproject_customer.databinding.RowAuctionCustomerDetailInfoBinding


class AuctionCustomerDetailInfoFragment : Fragment() {

    lateinit var auctionCustomerDetailInfoBinding: FragmentAuctionCustomerDetailInfoBinding
    lateinit var mainActivity: MainActivity

    lateinit var viewModel: AuctionCustomerDetailViewModel

    var imageNameList = mutableListOf<String>()

    var idx: Long = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auctionCustomerDetailInfoBinding = FragmentAuctionCustomerDetailInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[AuctionCustomerDetailViewModel::class.java]
        viewModel.auctionProductInfo.observe(mainActivity){
            var binding = auctionCustomerDetailInfoBinding
            binding.textViewAcDetail.text = it.auctionProductDetail

            imageNameList = it.auctionProductImageList!!
            auctionCustomerDetailInfoBinding.recyclerviewAcDetailInfo.adapter?.notifyDataSetChanged()
        }

        auctionCustomerDetailInfoBinding.run {
            recyclerviewAcDetailInfo.run {
                adapter = RecyclerviewAdaper()
                layoutManager = LinearLayoutManager(context)
            }
        }
        return auctionCustomerDetailInfoBinding.root
    }

    inner class RecyclerviewAdaper : RecyclerView.Adapter<RecyclerviewAdaper.viewholderclass>() {
        inner class viewholderclass(rowbinding: RowAuctionCustomerDetailInfoBinding) :
            RecyclerView.ViewHolder(rowbinding.root) {
            var auctiondetailinfoimg: ImageView

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
            return viewModel.auctionProductInfo.value?.auctionProductImageList?.size ?: 0
        }

        override fun onBindViewHolder(holder: viewholderclass, position: Int) {
            // 상세정보 이미지
            var fileName = viewModel.imageList.value?.get(position)!!
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.auctiondetailinfoimg)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionCustomerDetailViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        auctionCustomerDetailInfoBinding.root.requestLayout()

        viewModel = ViewModelProvider(mainActivity).get(AuctionCustomerDetailViewModel::class.java)
    }

}