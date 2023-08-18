package com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentStoreInfoCustomerDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsLinearBinding
import com.test.keepgardeningproject_customer.databinding.RowStoreInfoCustomerBinding

class StoreInfoCustomerDetailFragment : Fragment() {
    lateinit var fragmentStoreInfoCustomerDetailBinding: FragmentStoreInfoCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: StoreInfoCustomerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStoreInfoCustomerDetailBinding = FragmentStoreInfoCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentStoreInfoCustomerDetailBinding.run {
            toolbarStoreInfoDetail.run {
                title = "스토어 상세"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STORE_INFO_CUSTOMER_DETAIL_FRAGMENT)
                }
            }

            recyclerViewStoreInfoDetail.run {
                adapter = StoreInfoDetailRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentStoreInfoCustomerDetailBinding.root
    }

    inner class StoreInfoDetailRecyclerViewAdpater : RecyclerView.Adapter<StoreInfoDetailRecyclerViewAdpater.StoreInfoDetailViewHolder>() {
        inner class StoreInfoDetailViewHolder(rowHcsLinearBinding: RowHcsLinearBinding) :
            RecyclerView.ViewHolder(rowHcsLinearBinding.root) {

            var rowProductName: TextView
            var rowStoreName: TextView
            var rowPrice: TextView

            init {
                rowProductName = rowHcsLinearBinding.textViewHcsLinearTitle
                rowStoreName = rowHcsLinearBinding.textViewHcsLinearStore
                rowPrice = rowHcsLinearBinding.textViewHcsLinearPrice

                rowHcsLinearBinding.root.setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT, true, null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoDetailViewHolder {
            val rowHcsLinearBinding = RowHcsLinearBinding.inflate(layoutInflater)
            val storeInfoDetailViewHolder = StoreInfoDetailViewHolder(rowHcsLinearBinding)

            rowHcsLinearBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return storeInfoDetailViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: StoreInfoDetailViewHolder, position: Int) {
            holder.rowProductName.text = "상품명"
            holder.rowStoreName.text = "000스토어"
            holder.rowPrice.text = "10,000원"

        }
    }
}