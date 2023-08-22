package com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentOrderCheckFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderCheckFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderFormCustomerBinding
import org.w3c.dom.Text

class OrderCheckFormCustomerFragment : Fragment() {
    lateinit var fragmentOrderCheckFormCustomerBinding: FragmentOrderCheckFormCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: OrderCheckFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderCheckFormCustomerBinding = FragmentOrderCheckFormCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentOrderCheckFormCustomerBinding.run {
            toolbarOrderCheckForm.run {
                title = "주문확인서"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.CART_CUSTOMER_FRAGMENT)
                    mainActivity.removeFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT)
                    mainActivity.removeFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT)
                }
            }

            recyclerViewOrderCheckForm.run {
                adapter = OrderCheckFormRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
        }

        return fragmentOrderCheckFormCustomerBinding.root
    }

    inner class OrderCheckFormRecyclerViewAdpater : RecyclerView.Adapter<OrderCheckFormRecyclerViewAdpater.OrderCheckFormViewHolder>() {
        inner class OrderCheckFormViewHolder(rowOrderCheckFormCustomerBinding: RowOrderCheckFormCustomerBinding) :
            RecyclerView.ViewHolder(rowOrderCheckFormCustomerBinding.root) {

            var rowDeliveryState: TextView
            var rowStoreName: TextView
            var rowProductName: TextView
            var rowOption: TextView
            var rowOrderPriceValue: TextView
            var rowProductPriceValue: TextView

            init {
                rowDeliveryState = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormDeliveryState
                rowStoreName = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormStoreName
                rowProductName = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormProductName
                rowOption = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormOption
                rowOrderPriceValue = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormOrderPriceValue
                rowProductPriceValue = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormProductPriceValue
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCheckFormViewHolder {
            val rowOrderCheckFormCustomerBinding = RowOrderCheckFormCustomerBinding.inflate(layoutInflater)
            val orderCheckFormViewHolder = OrderCheckFormViewHolder(rowOrderCheckFormCustomerBinding)

            rowOrderCheckFormCustomerBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return orderCheckFormViewHolder
        }

        override fun getItemCount(): Int {
            return 5
        }

        override fun onBindViewHolder(holder: OrderCheckFormViewHolder, position: Int) {
            holder.rowDeliveryState.text = "배송완료"
            holder.rowStoreName.text = "푸른상점"
            holder.rowProductName.text = "몬스테라"
            holder.rowOption.text = "옵션 (2개)"
            holder.rowOrderPriceValue.text = "13,000원"
            holder.rowProductPriceValue.text = "26,000원"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                mainActivity.removeFragment(MainActivity.CART_CUSTOMER_FRAGMENT)
                mainActivity.removeFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT)
                mainActivity.removeFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }
}