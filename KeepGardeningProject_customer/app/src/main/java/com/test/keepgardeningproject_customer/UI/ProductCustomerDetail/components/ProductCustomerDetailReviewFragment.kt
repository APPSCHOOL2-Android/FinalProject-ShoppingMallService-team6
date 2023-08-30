package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.DAO.Review
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailViewModel
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailReviewBinding
import com.test.keepgardeningproject_customer.databinding.RowPcdrBinding

class ProductCustomerDetailReviewFragment : Fragment() {
    lateinit var fragmentProductcustomerDetailReviewBinding: FragmentProductCustomerDetailReviewBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel : ProductCustomerDetailViewModel

    var productIdx = MainActivity.chosedProductIdx
    var RL = mutableListOf<Review>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductcustomerDetailReviewBinding = FragmentProductCustomerDetailReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[ProductCustomerDetailViewModel::class.java]
        viewModel.run{
            this.getReviewByProduct(productIdx)
            reviewList.observe(mainActivity){
                RL = it
                fragmentProductcustomerDetailReviewBinding.recyclerPcdr.adapter?.notifyDataSetChanged()
                if(RL.size==0){
                    fragmentProductcustomerDetailReviewBinding.textViewPcdrReviewNumber.text = "아직 리뷰가 없어요"
                }else{
                    fragmentProductcustomerDetailReviewBinding.textViewPcdrReviewNumber.text = "리뷰 " + RL.size.toString() + " 개"
                }
            }
        }

        fragmentProductcustomerDetailReviewBinding.run{
            recyclerPcdr.run{
                adapter = RecyclerAdapterPCDR()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentProductcustomerDetailReviewBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentProductcustomerDetailReviewBinding.root.requestLayout()
    }


    inner class RecyclerAdapterPCDR : RecyclerView.Adapter<RecyclerAdapterPCDR.ViewHolderPCDR>(){
        inner class ViewHolderPCDR(rowPcdrBinding: RowPcdrBinding) : RecyclerView.ViewHolder(rowPcdrBinding.root){

            val textViewPcdrRatingNum : TextView
            val ratingBarPcdr : RatingBar
            val textViewPcdrUserName : TextView
            val textViewPcdrTitle : TextView
            val textViewPcdrContent : TextView


            init{
                textViewPcdrTitle = rowPcdrBinding.textViewPcdrTitle
                textViewPcdrRatingNum = rowPcdrBinding.textViewPcdrRatingNum
                textViewPcdrUserName = rowPcdrBinding.textViewPcdrUser
                textViewPcdrContent = rowPcdrBinding.textViewPcdrContent
                ratingBarPcdr = rowPcdrBinding.ratingBarPcdr
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPCDR {
            val rowPcdrBinding = RowPcdrBinding.inflate(layoutInflater)
            val viewHolderPCDR = ViewHolderPCDR(rowPcdrBinding)

            rowPcdrBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolderPCDR
        }

        override fun getItemCount(): Int {
            return RL.size
        }

        override fun onBindViewHolder(holder: ViewHolderPCDR, position: Int) {
            holder.textViewPcdrRatingNum.text = RL[position].rating.toString()
            holder.ratingBarPcdr.rating = RL[position].rating.toFloat()
            holder.textViewPcdrUserName.text = "작성자 : " + RL[position].storeName
            holder.textViewPcdrTitle.text = RL[position].reviewTitle
            holder.textViewPcdrContent.text = RL[position].reviewContent
        }
    }
}