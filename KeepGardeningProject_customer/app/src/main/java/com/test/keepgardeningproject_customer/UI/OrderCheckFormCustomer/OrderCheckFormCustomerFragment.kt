package com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.UI.OrderFormCustomer.OrderFormCustomerViewModel
import com.test.keepgardeningproject_customer.databinding.FragmentOrderCheckFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderCheckFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderFormCustomerBinding
import org.w3c.dom.Text
import java.text.DecimalFormat

class OrderCheckFormCustomerFragment : Fragment() {
    lateinit var fragmentOrderCheckFormCustomerBinding: FragmentOrderCheckFormCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var orderCheckFormCustomerViewModel: OrderCheckFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderCheckFormCustomerBinding = FragmentOrderCheckFormCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        orderCheckFormCustomerViewModel = ViewModelProvider(mainActivity)[OrderCheckFormCustomerViewModel::class.java]
        orderCheckFormCustomerViewModel.run {
//            orderCheckFormOrderList.observe(mainActivity) {
//                fragmentOrderCheckFormCustomerBinding.recyclerViewOrderCheckForm.adapter?.notifyDataSetChanged()
//            }
//            orderCheckFormOrderProductList.observe(mainActivity) {
//                fragmentOrderCheckFormCustomerBinding.recyclerViewOrderCheckForm.adapter?.notifyDataSetChanged()
//            }
//            orderCheckFormOrderImageList.observe(mainActivity) {
//                fragmentOrderCheckFormCustomerBinding.recyclerViewOrderCheckForm.adapter?.notifyDataSetChanged()
//            }
            orderCheckFormOrderList.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.recyclerViewOrderCheckForm.adapter?.notifyDataSetChanged()
            }
            orderCheckFormTotalOrderIdx.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormOrderNumber.text = "No. ${it.toString()}"
            }

            orderCheckFormDate.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormOrderDate.text = it
            }

            orderCheckFormReceiverName.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormReceiverName.text = it
            }

            orderCheckFormReceiverPhone.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormReceiverPhone.text = it
            }

            orderCheckFormAddress.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormAddress.text = it
            }

            orderCheckFormDeliveryRequest.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormDeliveryRequest.text = it
            }

            orderCheckFormOrdererName.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormOrdererName.text = it
            }

            orderCheckFormOrdererPhone.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormOrdererPhone.text = it
            }

            orderCheckFormOrdererEmail.observe(mainActivity) {
                fragmentOrderCheckFormCustomerBinding.textViewOrderCheckFormOrdererEmail.text = it
            }
        }

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

            val totalOrderIdx = arguments?.getLong("totalOrderIdx")!!

            // 주문 정보 받아오기
            orderCheckFormCustomerViewModel.getOrderInfo(totalOrderIdx)

            // 배송지, 주문자 정보 받아오기
            orderCheckFormCustomerViewModel.getDeliveryAndOrdererInfo(totalOrderIdx)
        }

        return fragmentOrderCheckFormCustomerBinding.root
    }

    inner class OrderCheckFormRecyclerViewAdpater :
        RecyclerView.Adapter<OrderCheckFormRecyclerViewAdpater.OrderCheckFormViewHolder>() {
        inner class OrderCheckFormViewHolder(rowOrderCheckFormCustomerBinding: RowOrderCheckFormCustomerBinding) :
            RecyclerView.ViewHolder(rowOrderCheckFormCustomerBinding.root) {

            var rowDeliveryState: TextView
            var rowProductName: TextView
            var rowOption: TextView
            var rowOrderPriceValue: TextView
            var rowProductPriceValue: TextView
            var rowImage: ImageView

            init {
                rowDeliveryState = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormDeliveryState
                rowProductName = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormProductName
                rowOption = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormOption
                rowOrderPriceValue = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormOrderPriceValue
                rowProductPriceValue = rowOrderCheckFormCustomerBinding.textViewRowOrderCheckFormProductPriceValue
                rowImage = rowOrderCheckFormCustomerBinding.imageViewRowOrderCheckFormProductImage
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
            return orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.size ?: 0
        }

        override fun onBindViewHolder(holder: OrderCheckFormViewHolder, position: Int) {
            var fileName = orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListImage!!
            CartRepository.getProductMainImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowImage)
            }

            holder.rowProductName.text =
                orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListproductName

            holder.rowDeliveryState.text =
                orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListdeliveryState

            holder.rowOption.text =
                "옵션 : ${orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListOption}개"

            var decimal = DecimalFormat("#,###")
            var price1 = orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListOrderPrice?.toInt()
            var price2 = orderCheckFormCustomerViewModel.orderCheckFormOrderList.value?.get(position)?.orderCheckFormListProductPrice?.toInt()
            holder.rowOrderPriceValue.text = decimal.format(price1) + "원"
            holder.rowProductPriceValue.text = decimal.format(price2) + "원"
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

    override fun onDestroy() {
        super.onDestroy()
        orderCheckFormCustomerViewModel.resetList()
    }
}