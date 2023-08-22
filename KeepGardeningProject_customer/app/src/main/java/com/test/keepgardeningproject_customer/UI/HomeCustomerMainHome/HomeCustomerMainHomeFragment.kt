package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import android.graphics.drawable.GradientDrawable.Orientation
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
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMainHomeBinding
import com.test.keepgardeningproject_customer.databinding.RowHcmhFavoriteBinding
import com.test.keepgardeningproject_customer.databinding.RowHcmhRecommendBinding

class HomeCustomerMainHomeFragment : Fragment() {

    lateinit var fragmentHomeCustomerMainHomeBinding: FragmentHomeCustomerMainHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeCustomerMainHomeBinding = FragmentHomeCustomerMainHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

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
            return 6
        }

        override fun onBindViewHolder(holder: ViewHolderHCMHRecommend, position: Int) {
            holder.textViewHcmhRecommendTitle.text = "상품명"
            holder.textViewHcmhRecommendPrice.text = "10,000원"
        }
    }
}