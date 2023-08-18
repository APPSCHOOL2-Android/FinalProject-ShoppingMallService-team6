package com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase

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
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerWishBinding

class MyPageCustomerPurchaseFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentMyPageCustomerPurchaseBinding: FragmentMyPageCustomerPurchaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMyPageCustomerPurchaseBinding = FragmentMyPageCustomerPurchaseBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentMyPageCustomerPurchaseBinding.run {
            recyclerviewPc.run {
                adapter = ResultRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
            toolbarPc.run {
                setTitle("구매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT)
                }
            }
        }


        return fragmentMyPageCustomerPurchaseBinding.root
    }

    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(rowWishListBinding: RowMyPageCustomerPurchaseBinding) : RecyclerView.ViewHolder(rowWishListBinding.root){

            val textViewPurchaseProductName: TextView
            val textViewPurchaseState: TextView
            val imageViewPurchaseimg: ImageView
            val buttonPurchaseBuy: Button


            init{
                textViewPurchaseProductName = rowWishListBinding.textViewPcProductName
                textViewPurchaseState = rowWishListBinding.textviewPcState
                imageViewPurchaseimg = rowWishListBinding.imageviewPcImg
                buttonPurchaseBuy = rowWishListBinding.buttonPcBuy

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowMyPageCustomerPurchaseBinding = RowMyPageCustomerPurchaseBinding.inflate(layoutInflater)
            val allViewHolder = ResultViewHolder(rowMyPageCustomerPurchaseBinding)

            rowMyPageCustomerPurchaseBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.textViewPurchaseProductName.text = "몬스테라"
            holder.textViewPurchaseState.text = "[결제완료]"
            holder.buttonPurchaseBuy.visibility = View.GONE
            if(position/2 ==0){
                holder.buttonPurchaseBuy.visibility = View.VISIBLE
            }
        }
    }


}