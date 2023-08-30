package com.test.keepgardeningproject_seller.UI.MyPageSellerProductState

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.OrdersProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.DAO.ProductState
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.OrderProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerProductStateBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageProductStateBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerQuaBinding
import org.w3c.dom.Text
import java.text.DecimalFormat

class MyPageSellerProductStateFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerProductStateFragment()
    }

    private lateinit var viewModel: MyPageSellerProductStateViewModel
    lateinit var binding: FragmentMyPageSellerProductStateBinding
    lateinit var mainActivity: MainActivity
    var ProductState: ProductState = ProductState("결제완료")

    // 받아올 데이타
    var PL = mutableListOf<ProductClass>()
    var PIL = mutableListOf<String>()
    var OPL = mutableListOf<OrdersProductClass>()

    // selected OrdersProductList
    var SPL = mutableListOf<ProductClass>()
    var SPIL = mutableListOf<String>()
    var SOPL = mutableListOf<OrdersProductClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageSellerProductStateBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[MyPageSellerProductStateViewModel::class.java]
        viewModel.run{
            this.getProductAll()
            this.getOrdersProductAll()
            productList.observe(mainActivity){
                // 상품리스트
                PL = it
                for(p in PL){
                    // 썸네일 리스트
                    PIL.add(p.productImageList[0])
                }
                orderProductList.observe(mainActivity){
                    OPL = it

                    var tempList = mutableListOf<ProductClass>()
                    // 데이터 정제시작
                    // 1. product 중 userSellerIdx 가 현재 로그인 사람것만 남김
                    for(product in PL){
                        if(product.productStoreIdx == mainActivity.loginSellerInfo.userSellerIdx){
                            tempList.add(product)
                        }
                    }

                    var tempList2 = mutableListOf<OrdersProductClass>()
                    var tempList3 = mutableListOf<ProductClass>()
                    var tempList4 = mutableListOf<String>()
                    // 2. 현재 userSellerIdx 가 올린 productIdx 가 들어간 ordersProduct 만 정제
                    for(op in OPL){
                        for(product in tempList){
                            if(op.ordersProductIdx == product.productIdx){
                                if(!tempList2.contains(op)){
                                    tempList2.add(op)
                                    tempList3.add(product)
                                    tempList4.add(product.productImageList[0])
                                }
                            }
                        }
                    }

                    SOPL = tempList2
                    SPL = tempList3
                    SPIL = tempList4


                    binding.recyclerViewSellerProductState.adapter?.notifyDataSetChanged()
                }
            }
        }

        binding.run {
            // 툴바
            materialToolbarSellerProductStateBar.run {
                title = "판매/배송"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT)
                }

            }

            // 리싸이클러
            recyclerViewSellerProductState.run {
                adapter = ProductStateRecyclerViewAdapter(ProductState)
                layoutManager = LinearLayoutManager(context)
            }

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerProductStateViewModel::class.java)
    }

    inner class ProductStateRecyclerViewAdapter(val productState: ProductState) :
        RecyclerView.Adapter<ProductStateRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(productStateBinding: RowMyPageProductStateBinding) :
            RecyclerView.ViewHolder(productStateBinding.root) {
            var imageViewSellerProduct : ImageView
            val productState: TextView
            val orderNumber: TextView
            val productName: TextView
            val productPrice: TextView
            val productCount: TextView
            val orderConfirmBtn: Button

            init {
                imageViewSellerProduct = productStateBinding.imageViewSellerProduct
                productState = productStateBinding.textViewSellerProductState
                orderNumber = productStateBinding.textviewSellerProductOrderNumber
                productName = productStateBinding.textviewSellerProductName
                productPrice = productStateBinding.sellerPrice
                productCount = productStateBinding.sellerCount
                orderConfirmBtn = productStateBinding.buttonSellerProductStateConvert

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowProductStateBinding = RowMyPageProductStateBinding.inflate(layoutInflater)
            val ViewHolder = ReviewViewHolder(rowProductStateBinding)

            rowProductStateBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return SOPL.size
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            // 이미지
            var fileName = SPIL[position]
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewSellerProduct)
            }

            // 상품명
            holder.productName.text = SPL[position].productName

            // 판매 상태
            if(SOPL[position].ordersDeliveryState == "결제완료"){
                holder.productState.text = "결제완료"
                holder.productState.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorPrimary))
            }
            else if(SOPL[position].ordersDeliveryState == "배송완료"){
                holder.productState.text = "배송완료"
                holder.productState.setTextColor(ContextCompat.getColor(mainActivity,R.color.deepblue))
            }


            // 주문번호
            holder.orderNumber.text = SOPL[position].ordersIdx.toString()

            // 가격
            val decimal = DecimalFormat("#,###")
            var temp = SOPL[position].ordersProductPrice.toString()
            holder.productPrice.text = decimal.format(temp.toInt()) + " 원"

            // 배송하기 버튼
            if(SOPL[position].ordersDeliveryState == "결제완료"){
                holder.orderConfirmBtn.text = "결제완료"
                holder.orderConfirmBtn.setBackgroundColor(ContextCompat.getColor(mainActivity,R.color.colorPrimary))
            }else{
                holder.orderConfirmBtn.text = "배송완료"
                holder.orderConfirmBtn.setBackgroundColor(ContextCompat.getColor(mainActivity,R.color.deepblue))
            }
            holder.orderConfirmBtn.setOnClickListener {
                if(SOPL[position].ordersDeliveryState == "결제완료"){
                    SOPL[position].ordersDeliveryState = "배송완료"
                    holder.orderConfirmBtn.text = "배송완료"
                    holder.orderConfirmBtn.setBackgroundColor(ContextCompat.getColor(mainActivity,R.color.deepblue))
                    holder.productState.text = "배송완료"
                    holder.productState.setTextColor(ContextCompat.getColor(mainActivity,R.color.deepblue))

                    OrderProductRepository.modifyOrdersProductInfo(SOPL[position]){
                        Snackbar.make(binding.root, "주문번호 ${SOPL[position].ordersIdx.toString()} 배송완료", Snackbar.LENGTH_SHORT).show()
                        holder.orderConfirmBtn.text = "배송완료"
                    }
                }else if(SOPL[position].ordersDeliveryState == "배송완료"){
                    Snackbar.make(binding.root, "이미 배송이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }


        }
    }

}