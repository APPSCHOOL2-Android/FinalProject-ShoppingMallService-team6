package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMainHomeBinding
import com.test.keepgardeningproject_customer.databinding.RowHcmhFavoriteBinding
import com.test.keepgardeningproject_customer.databinding.RowHcmhRecommendBinding
import java.text.DecimalFormat

class HomeCustomerMainHomeFragment : Fragment() {

    lateinit var fragmentHomeCustomerMainHomeBinding: FragmentHomeCustomerMainHomeBinding
    lateinit var mainActivity: MainActivity

    lateinit var viewModel : HomeCustomerMainHomeViewModel

    lateinit var productList : MutableList<ProductClass>
    lateinit var favoriteList : MutableList<ProductClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeCustomerMainHomeBinding = FragmentHomeCustomerMainHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 추천상품 동기화
        viewModel = ViewModelProvider(mainActivity)[HomeCustomerMainHomeViewModel::class.java]
        viewModel.run{
            productClassList.observe(mainActivity){
                productList = it

                ordersProductIdxList.observe(mainActivity){
                    var idxList = it
                    idxList.distinct()
                    val tempList = mutableListOf<ProductClass>()

                    for((index,product) in productList.withIndex()){
                        for(idx in idxList){
                            if(product.productIdx == idx && !tempList.contains(product)){
                                tempList.add(product)
                            }
                        }
                    }

                    tempList.distinct()
                    favoriteList = tempList

                    fragmentHomeCustomerMainHomeBinding.recyclerHcmhRecommend.adapter?.notifyDataSetChanged()
                    fragmentHomeCustomerMainHomeBinding.recyclerHcmhFavoriteGoods.adapter?.notifyDataSetChanged()
                }
            }
            productImageNameList.observe(mainActivity){
                fragmentHomeCustomerMainHomeBinding.recyclerHcmhRecommend.adapter?.notifyDataSetChanged()
            }
        }
        viewModel.getProductInfoAll()

        // 인기상품 동기화
        viewModel.getOrdersInfoAll()

        fragmentHomeCustomerMainHomeBinding.run{
            // 메뉴선택
            run{
                val bundle = Bundle()
                // 다육식물
                btnHcmhMenu1.setOnClickListener {
                    bundle.putString("category","다육식물")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 동서양란
                btnHcmhMenu2.setOnClickListener {
                    bundle.putString("category","동서양란")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 관엽식물
                btnHcmhMenu3.setOnClickListener {
                    bundle.putString("category","관엽식물")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 분재
                btnHcmhMenu4.setOnClickListener {
                    bundle.putString("category","분재")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 경매
                btnHcmhMenu5.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.AUCTION_CUSTOMER_FRAGMENT,true,null)
                }
                // 농산물
                btnHcmhMenu6.setOnClickListener {
                    bundle.putString("category","농산물")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 다육식물
                btnHcmhMenu7.setOnClickListener {
                    bundle.putString("category","씨앗/묘목")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 꽃다발
                btnHcmhMenu8.setOnClickListener {
                    bundle.putString("category","꽃다발")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 원예자재류
                btnHcmhMenu9.setOnClickListener {
                    bundle.putString("category","원예자재류")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
                // 천원샵
                btnHcmhMenu10.setOnClickListener {
                    bundle.putString("category","천원샵")
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,bundle)
                }
            }

            // 인기상품
            recyclerHcmhFavoriteGoods.run{
                adapter = RecyclerAdapterHCMHFavorite()
                layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            }

            // 추천상품
            recyclerHcmhRecommend.run{
                adapter = RecyclerAdapterHCMHRecommend()
                layoutManager = GridLayoutManager(context,2)
            }

        }

        return fragmentHomeCustomerMainHomeBinding.root
    }

    //인기상품 리싸이클러 어댑터
    inner class RecyclerAdapterHCMHFavorite: RecyclerView.Adapter<RecyclerAdapterHCMHFavorite.ViewHolderHCMHFavorite>(){
        inner class ViewHolderHCMHFavorite(rowHcmhFavoriteBinding: RowHcmhFavoriteBinding) : RecyclerView.ViewHolder(rowHcmhFavoriteBinding.root){
            val imageViewHcmhFavorite : ImageView
            val textViewHcmhFavoriteTitle : TextView
            val textViewHcmhFavoritePrice : TextView

            init{
                imageViewHcmhFavorite = rowHcmhFavoriteBinding.imageViewHcmhFavorite
                textViewHcmhFavoriteTitle = rowHcmhFavoriteBinding.textViewHcmhFavoriteTitle
                textViewHcmhFavoritePrice = rowHcmhFavoriteBinding.textViewHcmhFavoritePrice

                // 아이템 클릭시 개별 상품상세로 이동
                rowHcmhFavoriteBinding.root.setOnClickListener {
                    val selectedProductIdx = favoriteList[adapterPosition].productIdx!!
                    MainActivity.chosedProductIdx = selectedProductIdx
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT,true,null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCMHFavorite {
            val rowHcmhFavoriteBinding = RowHcmhFavoriteBinding.inflate(layoutInflater)
            val viewHolderHCMHFavorite = ViewHolderHCMHFavorite(rowHcmhFavoriteBinding)

//            rowHcmhFavoriteBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            return viewHolderHCMHFavorite
        }

        override fun getItemCount(): Int {
            return favoriteList.size
        }

        override fun onBindViewHolder(holder: ViewHolderHCMHFavorite, position: Int) {
            // image
            // 이미지 썸네일 넣기(대표 사진 0번)
            var fileName = favoriteList[position].productImageList?.get(0)!!
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewHcmhFavorite)
            }

            // 상품명
            holder.textViewHcmhFavoriteTitle.text = favoriteList[position].productName

            // 가격
            var decimal = DecimalFormat("#,###")
            var temp = favoriteList[position].productPrice?.toInt()!!
            holder.textViewHcmhFavoritePrice.text = decimal.format(temp) + "원"
        }
    }

    // 추천 상품 리싸이클러 어댑터
    inner class RecyclerAdapterHCMHRecommend : RecyclerView.Adapter<RecyclerAdapterHCMHRecommend.ViewHolderHCMHRecommend>(){
        inner class ViewHolderHCMHRecommend(rowHcmhRecommendBinding: RowHcmhRecommendBinding) : RecyclerView.ViewHolder(rowHcmhRecommendBinding.root){
            val imageViewHcmhRecommend : ImageView
            val textViewHcmhRecommendTitle : TextView
            val textViewHcmhRecommendPrice : TextView

            init {
                imageViewHcmhRecommend = rowHcmhRecommendBinding.imageViewHcmhRecommend
                textViewHcmhRecommendTitle = rowHcmhRecommendBinding.textViewHcmhRecommendTitle
                textViewHcmhRecommendPrice = rowHcmhRecommendBinding.textViewHcmhRecommendPrice

                // 클릭시 개별 아이템 상세페이지 이동
                rowHcmhRecommendBinding.root.setOnClickListener {
                    val selectedProductIdx = viewModel.productClassList.value?.get(adapterPosition)?.productIdx!!
                    MainActivity.chosedProductIdx = selectedProductIdx
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT,true,null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCMHRecommend {
            val rowHcmhRecommendBinding = RowHcmhRecommendBinding.inflate(layoutInflater)
            val viewHolderHCMHRecommend = ViewHolderHCMHRecommend(rowHcmhRecommendBinding)

//            rowHcmhRecommendBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            return viewHolderHCMHRecommend
        }

        override fun getItemCount(): Int {
            return viewModel.productClassList.value?.size!!
        }

        override fun onBindViewHolder(holder: ViewHolderHCMHRecommend, position: Int) {
            // 이미지 썸네일 넣기(대표 사진 0번)
            var fileName = viewModel.productImageNameList.value?.get(position)!!
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewHcmhRecommend)
            }

            // 제목 표시하기 (1줄 고정)
            holder.textViewHcmhRecommendTitle.text = viewModel.productClassList.value?.get(position)?.productName

            // 숫자 comma 표시하기
            var decimal = DecimalFormat("#,###")
            var temp = viewModel.productClassList.value?.get(position)?.productPrice?.toInt()
            holder.textViewHcmhRecommendPrice.text = decimal.format(temp) + "원"
        }
    }
}