package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailReviewBinding
import com.test.keepgardeningproject_customer.databinding.RowPcdrBinding

class ProductCustomerDetailReviewFragment : Fragment() {
    lateinit var fragmentProductcustomerDetailReviewBinding: FragmentProductCustomerDetailReviewBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductcustomerDetailReviewBinding = FragmentProductCustomerDetailReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

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
            val textViewPcdrTitle : TextView
            val textViewPcdrContent : TextView


            init{
                textViewPcdrTitle = rowPcdrBinding.textViewPcdrTitle
                textViewPcdrContent = rowPcdrBinding.textViewPcdrContent
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPCDR {
            val rowPcdrBinding = RowPcdrBinding.inflate(layoutInflater)
            val viewHolderPCDR = ViewHolderPCDR(rowPcdrBinding)

//            rowPcdrBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            return viewHolderPCDR
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: ViewHolderPCDR, position: Int) {
            holder.textViewPcdrTitle.text = "상품리뷰 제목"
            holder.textViewPcdrContent.text = "상품리뷰 내용"
        }
    }
}