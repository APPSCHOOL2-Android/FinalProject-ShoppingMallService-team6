package com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.StoreRepository
import com.test.keepgardeningproject_customer.databinding.FragmentStoreInfoCustomerDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsGridBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsLinearBinding
import java.text.DecimalFormat

class StoreInfoCustomerDetailFragment : Fragment() {
    lateinit var fragmentStoreInfoCustomerDetailBinding: FragmentStoreInfoCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    private lateinit var storeInfoCustomerDetailViewModel: StoreInfoCustomerDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentStoreInfoCustomerDetailBinding = FragmentStoreInfoCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        storeInfoCustomerDetailViewModel = ViewModelProvider(mainActivity)[StoreInfoCustomerDetailViewModel::class.java]
        storeInfoCustomerDetailViewModel.run {
            storeName.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.textViewStoreInfoDetailStoreName.text = it
            }
            storeAddress.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.textViewStoreInfoDetailAddress.text = it
            }
            storeDetail.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.textViewStoreInfoDetailInfo.text = it
            }
            storeImage.observe(mainActivity) {
                if (it == "None") {
                    fragmentStoreInfoCustomerDetailBinding.imageViewStoreInfoDetailMain.setImageResource(R.mipmap.app)
                }
            }
            storeBitmap.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.imageViewStoreInfoDetailMain.setImageBitmap(it)
            }
            productList.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.recyclerViewStoreInfoDetail.adapter?.notifyDataSetChanged()
            }
            productImageList.observe(mainActivity) {
                fragmentStoreInfoCustomerDetailBinding.recyclerViewStoreInfoDetail.adapter?.notifyDataSetChanged()
            }
        }

        fragmentStoreInfoCustomerDetailBinding.run {
            toolbarStoreInfoDetail.run {
                title = "스토어 상세"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STORE_INFO_CUSTOMER_DETAIL_FRAGMENT)
                }
            }

            // 표시 형식 그리드로 변경
            buttonStoreInfoDetailGrid.setOnClickListener {
                recyclerViewStoreInfoDetail.adapter = StoreInfoDetailRecyclerViewGridAdpater()
                recyclerViewStoreInfoDetail.layoutManager = GridLayoutManager(context, 2)
            }

            // 표시 형식 리스트로 변경
            buttonStoreInfoDetailList.setOnClickListener {
                recyclerViewStoreInfoDetail.adapter = StoreInfoDetailRecyclerViewAdpater()
                recyclerViewStoreInfoDetail.layoutManager = LinearLayoutManager(context)
            }

            recyclerViewStoreInfoDetail.run {
                adapter = StoreInfoDetailRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }

            imageViewStoreInfoDetailMain.setImageResource(R.mipmap.app)
        }

        val storeIdx = arguments?.getLong("storeIdx")!!
        storeInfoCustomerDetailViewModel.getStoreInfo(storeIdx)
        storeInfoCustomerDetailViewModel.getStoreProduct(storeIdx)

        return fragmentStoreInfoCustomerDetailBinding.root
    }

    // 목록을 리스트로 표시하는 어댑터
    inner class StoreInfoDetailRecyclerViewAdpater : RecyclerView.Adapter<StoreInfoDetailRecyclerViewAdpater.StoreInfoDetailViewHolder>() {
        inner class StoreInfoDetailViewHolder(rowHcsLinearBinding: RowHcsLinearBinding) :
                RecyclerView.ViewHolder(rowHcsLinearBinding.root) {

            var rowProductImage: ImageView
            var rowProductName: TextView
            var rowStoreName: TextView
            var rowPrice: TextView

            init {
                rowProductImage = rowHcsLinearBinding.imageHcsLinear
                rowProductName = rowHcsLinearBinding.textViewHcsLinearTitle
                rowStoreName = rowHcsLinearBinding.textViewHcsLinearStore
                rowPrice = rowHcsLinearBinding.textViewHcsLinearPrice

                rowHcsLinearBinding.root.setOnClickListener {
                    val selectedProductIdx = storeInfoCustomerDetailViewModel.productList.value?.get(adapterPosition)?.productIdx!!
                    val bundle = Bundle()
                    bundle.putLong("selectedProductIdx", selectedProductIdx)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT, true, bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoDetailViewHolder {
            val rowHcsLinearBinding = RowHcsLinearBinding.inflate(layoutInflater)
            val storeInfoDetailViewHolder = StoreInfoDetailViewHolder(rowHcsLinearBinding)

            rowHcsLinearBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return storeInfoDetailViewHolder
        }

        override fun getItemCount(): Int {
            return storeInfoCustomerDetailViewModel.productList.value?.size!!
        }

        override fun onBindViewHolder(holder: StoreInfoDetailViewHolder, position: Int) {
            // 상품명
            holder.rowProductName.text = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productName

            // 상품 가격
            var decimal = DecimalFormat("#,###")
            var price = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productPrice?.toLong()
            holder.rowPrice.text = decimal.format(price) + " 원"

            // 스토어 이름
            val storeIdx = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productStoreIdx!!
            StoreRepository.getProductSellerInfoByIdx(storeIdx) {
                for (c1 in it.result.children) {
                    holder.rowStoreName.text = c1.child("userSellerStoreName").value as String
                }
            }

            // 상품 이미지
            var fileName = storeInfoCustomerDetailViewModel.productImageList.value?.get(position)!!
            StoreRepository.getImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowProductImage)
            }
        }
    }

    // 목록을 그리드로 표시하는 어댑터
    inner class StoreInfoDetailRecyclerViewGridAdpater : RecyclerView.Adapter<StoreInfoDetailRecyclerViewGridAdpater.StoreInfoDetailViewHolder>() {
        inner class StoreInfoDetailViewHolder(rowHcsGridBinding: RowHcsGridBinding) :
            RecyclerView.ViewHolder(rowHcsGridBinding.root) {

            var rowProductImage: ImageView
            var rowProductName: TextView
            var rowStoreName: TextView
            var rowPrice: TextView

            init {
                rowProductImage = rowHcsGridBinding.imageViewHcsGrid
                rowProductName = rowHcsGridBinding.textViewHcsGridTitle
                rowStoreName = rowHcsGridBinding.textViewHcsGridStore
                rowPrice = rowHcsGridBinding.textViewHcsGridStore

                rowHcsGridBinding.root.setOnClickListener {
                    val selectedProductIdx = storeInfoCustomerDetailViewModel.productList.value?.get(adapterPosition)?.productIdx!!
                    val bundle = Bundle()
                    bundle.putLong("selectedProductIdx", selectedProductIdx)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT, true, bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoDetailViewHolder {
            val rowHcsGridBinding = RowHcsGridBinding.inflate(layoutInflater)
            val storeInfoDetailViewHolder = StoreInfoDetailViewHolder(rowHcsGridBinding)

            rowHcsGridBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return storeInfoDetailViewHolder
        }

        override fun getItemCount(): Int {
            return storeInfoCustomerDetailViewModel.productList.value?.size!!
        }

        override fun onBindViewHolder(holder: StoreInfoDetailViewHolder, position: Int) {
            // 상품명
            holder.rowProductName.text = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productName

            // 상품 가격
            var decimal = DecimalFormat("#,###")
            var price = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productPrice?.toLong()
            holder.rowPrice.text = decimal.format(price) + " 원"

            // 스토어 이름
            val storeIdx = storeInfoCustomerDetailViewModel.productList.value?.get(position)?.productStoreIdx!!
            StoreRepository.getProductSellerInfoByIdx(storeIdx) {
                for (c1 in it.result.children) {
                    holder.rowStoreName.text = c1.child("userSellerStoreName").value as String
                }
            }

            // 상품 이미지
            var fileName = storeInfoCustomerDetailViewModel.productImageList.value?.get(position)!!
            StoreRepository.getImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowProductImage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        storeInfoCustomerDetailViewModel.reset()
    }
}