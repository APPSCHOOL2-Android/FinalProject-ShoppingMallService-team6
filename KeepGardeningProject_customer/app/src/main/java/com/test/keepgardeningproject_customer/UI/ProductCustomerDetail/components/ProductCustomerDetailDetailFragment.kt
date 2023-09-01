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
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailViewModel
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowPcddBinding


class ProductCustomerDetailDetailFragment : Fragment() {
    lateinit var fragmentProductCustomerDetailDetailBinding: FragmentProductCustomerDetailDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var viewModel : ProductCustomerDetailViewModel

    var fileNameList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentProductCustomerDetailDetailBinding = FragmentProductCustomerDetailDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[ProductCustomerDetailViewModel::class.java]

        viewModel.productInfo.observe(mainActivity){
            var binding = fragmentProductCustomerDetailDetailBinding
            // 상세정보
            binding.textViewPcddDetail.text = it.productDetail

            fileNameList = it.productImageList!!
            fragmentProductCustomerDetailDetailBinding.recyclerPcdd.adapter?.notifyDataSetChanged()
        }

        fragmentProductCustomerDetailDetailBinding.run{
            recyclerPcdd.run{
                adapter = RecyclerAdapterPCDD()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentProductCustomerDetailDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductCustomerDetailViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        fragmentProductCustomerDetailDetailBinding.root.requestLayout()
        viewModel = ViewModelProvider(mainActivity).get(ProductCustomerDetailViewModel::class.java)
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
            return viewModel.productInfo.value?.productImageList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolderPCDD, position: Int) {
            // 상세정보 이미지

            var fileName = viewModel.imageList.value?.get(position)!!
            ProductRepository.getProductImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imagePcddRow)
            }
        }
    }
}