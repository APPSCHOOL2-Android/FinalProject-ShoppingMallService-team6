package com.test.keepgardeningproject_customer.UI.MyPageCustomerWish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerWishBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerAuctionBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerWishBinding

class MyPageCustomerWishFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var myPageCustomerWishViewModel: MyPageCustomerWishViewModel
    lateinit var myPageCustomerWishBinding: FragmentMyPageCustomerWishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageCustomerWishBinding = FragmentMyPageCustomerWishBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        myPageCustomerWishBinding.run {
            recyclerviewWc.run {
                adapter = ResultRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
            toolbarWc.run {
                setTitle("찜 내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_WISH_FRAGMENT)
                }
            }
        }

        return myPageCustomerWishBinding.root

    }
    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(rowWishListBinding: RowMyPageCustomerWishBinding) : RecyclerView.ViewHolder(rowWishListBinding.root){

            val textViewWishProductName: TextView
            val textViewWishStoreName: TextView
            val textViewWishPrice: TextView
            val imageViewWishimg:ImageView
            val imageViewWishHeartIcon:ImageView

            init{
                textViewWishProductName = rowWishListBinding.textViewWcProductName
                textViewWishStoreName = rowWishListBinding.textViewWcStoreName
                textViewWishPrice = rowWishListBinding.textViewWcPrice
                imageViewWishimg = rowWishListBinding.imageViewWcImg
                imageViewWishHeartIcon = rowWishListBinding.imageViewWcHeartIcon
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowMyPageCustomerWishBinding = RowMyPageCustomerWishBinding.inflate(layoutInflater)
            val allViewHolder = ResultViewHolder(rowMyPageCustomerWishBinding)

            rowMyPageCustomerWishBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.textViewWishPrice.text = "13000원"
            holder.textViewWishProductName.text = "몬스테라"
            holder.textViewWishStoreName.text = "스토어"
        }
    }


}