package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import android.net.Uri
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

    lateinit var homeCustomerMainHomeViewModel : HomeCustomerMainHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeCustomerMainHomeBinding = FragmentHomeCustomerMainHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 인기상품 동기화


        // 추천상품 동기화
        homeCustomerMainHomeViewModel = ViewModelProvider(mainActivity)[HomeCustomerMainHomeViewModel::class.java]
        homeCustomerMainHomeViewModel.run{
            productClassList.observe(mainActivity){
                fragmentHomeCustomerMainHomeBinding.recyclerHcmhRecommend.adapter?.notifyDataSetChanged()
            }
            productImageNameList.observe(mainActivity){
                fragmentHomeCustomerMainHomeBinding.recyclerHcmhRecommend.adapter?.notifyDataSetChanged()
            }
        }
        homeCustomerMainHomeViewModel.getProductInfoAll()

        fragmentHomeCustomerMainHomeBinding.run{
            // 메뉴선택
            run{
                btnHcmhMenu1.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu2.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu3.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu4.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu5.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu6.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu7.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu8.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu9.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT,true,null)
                }
                btnHcmhMenu10.setOnClickListener {
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
            return 6
        }

        override fun onBindViewHolder(holder: ViewHolderHCMHFavorite, position: Int) {
            holder.textViewHcmhFavoriteTitle.text = "상품명"
            holder.textViewHcmhFavoritePrice.text = "10,000원"
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
                    val selectedProductIdx = homeCustomerMainHomeViewModel.productClassList.value?.get(adapterPosition)?.productIdx!!
                    val bundle = Bundle()
                    bundle.putLong("selectedProductIdx",selectedProductIdx)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT,true,bundle)
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
            return homeCustomerMainHomeViewModel.productClassList.value?.size!!
        }

        override fun onBindViewHolder(holder: ViewHolderHCMHRecommend, position: Int) {
            // 이미지 썸네일 넣기(대표 사진 0번)
            var fileName = homeCustomerMainHomeViewModel.productImageNameList.value?.get(position)!!
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewHcmhRecommend)
            }

            // 제목 표시하기 (1줄 고정)
            holder.textViewHcmhRecommendTitle.text = homeCustomerMainHomeViewModel.productClassList.value?.get(position)?.productName

            // 숫자 comma 표시하기
            var decimal = DecimalFormat("#,###")
            var temp = homeCustomerMainHomeViewModel.productClassList.value?.get(position)?.productPrice?.toInt()
            holder.textViewHcmhRecommendPrice.text = decimal.format(temp) + "원"
        }
    }
}