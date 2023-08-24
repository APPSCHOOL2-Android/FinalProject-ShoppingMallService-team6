package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailFragment
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailViewModel
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowPcddBinding


class ProductCustomerDetailDetailFragment : Fragment() {
    lateinit var fragmentProductCustomerDetailDetailBinding: FragmentProductCustomerDetailDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var productCustomerDetailViewModel : ProductCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductCustomerDetailDetailBinding = FragmentProductCustomerDetailDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        productCustomerDetailViewModel = ViewModelProvider(mainActivity)[ProductCustomerDetailViewModel::class.java]

        productCustomerDetailViewModel.productInfo.observe(mainActivity){
            var binding = fragmentProductCustomerDetailDetailBinding
            // 상세정보
            binding.textViewPcddDetail.text = it.productDetail
        }

        fragmentProductCustomerDetailDetailBinding.run{
            recyclerPcdd.run{
                adapter = RecyclerAdapterPCDD()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentProductCustomerDetailDetailBinding.root
    }

    override fun onResume() {
        super.onResume()
        fragmentProductCustomerDetailDetailBinding.root.requestLayout()
    }


    inner class RecyclerAdapterPCDD : RecyclerView.Adapter<RecyclerAdapterPCDD.ViewHolderPCDD>(){
        inner class ViewHolderPCDD(rowPcddBinding: RowPcddBinding) : RecyclerView.ViewHolder(rowPcddBinding.root){
            val imagePcddRow : ImageView

            init{
                imagePcddRow = rowPcddBinding.imagePcddRow
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPCDD {
            val rowPcddBinding = RowPcddBinding.inflate(layoutInflater)
            val viewHolderPCDD = ViewHolderPCDD(rowPcddBinding)

            rowPcddBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolderPCDD
        }

        override fun getItemCount(): Int {
            return productCustomerDetailViewModel.productInfo.value?.productImageList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolderPCDD, position: Int) {
            // 상세정보 이미지
            var fileNameList = productCustomerDetailViewModel.productInfo.value?.productImageList!!
            for(fileName in fileNameList){
                ProductRepository.getProductImage(fileName){
                    var fileUri = it.result
                    Glide.with(mainActivity).load(fileUri).into(holder.imagePcddRow)
                }
            }
        }
    }
}