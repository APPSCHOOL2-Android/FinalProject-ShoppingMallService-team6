package com.test.keepgardeningproject_customer.UI.CartCustomer

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
import com.test.keepgardeningproject_customer.databinding.FragmentCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderCheckFormCustomerBinding

class CartCustomerFragment : Fragment() {
    lateinit var fragmentCartCustomerBinding: FragmentCartCustomerBinding
    private lateinit var viewModel: CartCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCartCustomerBinding = FragmentCartCustomerBinding.inflate(inflater)

        fragmentCartCustomerBinding.run {
            toolbarCart.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                title = "장바구니"
            }

            recyclerViewCart.run {
                adapter = CartRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentCartCustomerBinding.root
    }

    inner class CartRecyclerViewAdpater : RecyclerView.Adapter<CartRecyclerViewAdpater.CartViewHolder>() {
        inner class CartViewHolder(rowCartCustomerBinding: RowCartCustomerBinding) :
            RecyclerView.ViewHolder(rowCartCustomerBinding.root) {

            var rowProductName: TextView
            var rowPriceValue: TextView
            var rowCountValue: TextView

            init {
                rowProductName = rowCartCustomerBinding.textViewRowCartProductName
                rowPriceValue = rowCartCustomerBinding.textViewRowCartPriceValue
                rowCountValue = rowCartCustomerBinding.textViewRowCartCountValue
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val rowCartCustomerBinding = RowCartCustomerBinding.inflate(layoutInflater)
            val cartViewHolder = CartViewHolder(rowCartCustomerBinding)

            rowCartCustomerBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return cartViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            holder.rowProductName.text = "몬스테라"
            holder.rowPriceValue.text = "26,000원"
            holder.rowCountValue.text = "2"
        }
    }
}