package com.test.keepgardeningproject_customer.UI.StoreInfoCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentStoreInfoCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowStoreInfoCustomerBinding

class StoreInfoCustomerFragment : Fragment() {
    lateinit var fragmentStoreInfoCustomerBinding: FragmentStoreInfoCustomerBinding
    private lateinit var viewModel: StoreInfoCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStoreInfoCustomerBinding = FragmentStoreInfoCustomerBinding.inflate(inflater)

        fragmentStoreInfoCustomerBinding.run {
            toolbarStoreInfo.run {
                title = "스토어"
                setNavigationIcon(R.drawable.ic_back_24px)
            }

            recyclerViewStoreInfo.run {
                adapter = StoreInfoRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentStoreInfoCustomerBinding.root
    }

    inner class StoreInfoRecyclerViewAdpater : RecyclerView.Adapter<StoreInfoRecyclerViewAdpater.StoreInfoViewHolder>() {
        inner class StoreInfoViewHolder(rowStoreInfoCustomerBinding: RowStoreInfoCustomerBinding) :
            RecyclerView.ViewHolder(rowStoreInfoCustomerBinding.root) {

            var rowStoreName: TextView

            init {
                rowStoreName = rowStoreInfoCustomerBinding.textViewRowStoreInfoStoreName
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoViewHolder {
            val rowStoreInfoCustomerBinding = RowStoreInfoCustomerBinding.inflate(layoutInflater)
            val storeInfoViewHolder = StoreInfoViewHolder(rowStoreInfoCustomerBinding)

            rowStoreInfoCustomerBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return storeInfoViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: StoreInfoViewHolder, position: Int) {
            holder.rowStoreName.text = "000스토어"
        }
    }
}