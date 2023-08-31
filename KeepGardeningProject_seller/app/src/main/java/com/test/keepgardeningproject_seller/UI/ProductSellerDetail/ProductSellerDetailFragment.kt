package com.test.keepgardeningproject_seller.UI.ProductSellerDetail

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment.Companion.productIdx
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainViewModel
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerDetailBinding
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerMainBinding
import com.test.keepgardeningproject_seller.databinding.RowAuctionSellerDetailBinding

class ProductSellerDetailFragment : Fragment() {

    lateinit var fragmentProductSellerDetailBinding: FragmentProductSellerDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var productSellerMainViewModel: ProductSellerMainViewModel

    var fileNameList = mutableListOf<String>()

    companion object {
        fun newInstance() = ProductSellerDetailFragment()
    }

    private lateinit var viewModel: ProductSellerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerDetailBinding = FragmentProductSellerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        productSellerMainViewModel = ViewModelProvider(mainActivity)[ProductSellerMainViewModel::class.java]
        productSellerMainViewModel.run {

            productDetail.observe(mainActivity) {
                fragmentProductSellerDetailBinding.textViewProductSellerDetailProductDetail.text = it
                fragmentProductSellerDetailBinding.recyclerViewProductSellerDetail.adapter?.notifyDataSetChanged()
            }
            productImageNameList.observe(mainActivity) {
                fileNameList = it
                fragmentProductSellerDetailBinding.recyclerViewProductSellerDetail.adapter?.notifyDataSetChanged()
            }
        }
        productSellerMainViewModel.getProductInfo(productIdx.toLong())

        fragmentProductSellerDetailBinding.run {
            recyclerViewProductSellerDetail.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
                adapter?.notifyDataSetChanged()
            }
        }
        return fragmentProductSellerDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        fileNameList.clear()

        productSellerMainViewModel.getProductInfo(productIdx.toLong())

        var adapter = fragmentProductSellerDetailBinding.recyclerViewProductSellerDetail.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()

        fragmentProductSellerDetailBinding.root.requestLayout()
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

            var fileName = fileNameList.get(position)!!
            ProductRepository.getProductImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewProductDetail)
            }
        }
    }

}