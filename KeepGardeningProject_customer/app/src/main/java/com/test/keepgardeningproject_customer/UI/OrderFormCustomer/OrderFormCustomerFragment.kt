package com.test.keepgardeningproject_customer.UI.OrderFormCustomer

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
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentOrderFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderFormCustomerBinding

class OrderFormCustomerFragment : Fragment() {
    lateinit var fragmentOrderFormCustomerBinding: FragmentOrderFormCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: OrderFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderFormCustomerBinding = FragmentOrderFormCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentOrderFormCustomerBinding.run {
            toolbarOrderForm.run {
                title = "주문서"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT)
                }
            }
            recyclerViewOrderForm.run {
                adapter = OrderFormRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

            buttonOrderFormSubmitOrder.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT, true, null)
            }
        }

        return fragmentOrderFormCustomerBinding.root
    }

    inner class OrderFormRecyclerViewAdpater : RecyclerView.Adapter<OrderFormRecyclerViewAdpater.OrderFormViewHolder>() {
        inner class OrderFormViewHolder(rowOrderFormCustomerBinding: RowOrderFormCustomerBinding) :
            RecyclerView.ViewHolder(rowOrderFormCustomerBinding.root) {


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderFormViewHolder {
            val rowOrderFormCustomerBinding = RowOrderFormCustomerBinding.inflate(layoutInflater)
            val orderFormViewHolder = OrderFormViewHolder(rowOrderFormCustomerBinding)

            rowOrderFormCustomerBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return orderFormViewHolder
        }

        override fun getItemCount(): Int {
            return 2
        }

        override fun onBindViewHolder(holder: OrderFormViewHolder, position: Int) {

        }
    }


}