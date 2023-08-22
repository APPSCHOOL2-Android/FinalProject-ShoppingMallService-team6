package com.test.keepgardeningproject_seller.UI.OrderCheckFormSeller

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
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentOrderCheckFormSellerBinding
import com.test.keepgardeningproject_seller.databinding.RowOrderCheckFormSellerBinding

class OrderCheckFormSellerFragment : Fragment() {
    lateinit var fragmentOrderCheckFormSellerBinding: FragmentOrderCheckFormSellerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: OrderCheckFormSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrderCheckFormSellerBinding = FragmentOrderCheckFormSellerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentOrderCheckFormSellerBinding.run {
            toolbarOrderCheckForm.run {
                title = "주문확인서"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ORDER_CHECK_FORM_SELLER_FRAGMENT)
                }
            }



            recyclerViewOrderCheckForm.run {
                adapter = OrderCheckFormRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
        }

        return fragmentOrderCheckFormSellerBinding.root
    }

    inner class OrderCheckFormRecyclerViewAdpater : RecyclerView.Adapter<OrderCheckFormRecyclerViewAdpater.OrderCheckFormViewHolder>() {
        inner class OrderCheckFormViewHolder(rowOrderCheckFormSellerBinding: RowOrderCheckFormSellerBinding) :
            RecyclerView.ViewHolder(rowOrderCheckFormSellerBinding.root) {

            var rowDeliveryState: TextView
            var rowStoreName: TextView
            var rowProductName: TextView
            var rowOption: TextView
            var rowOrderPriceValue: TextView
            var rowProductPriceValue: TextView

            init {
                rowDeliveryState = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormDeliveryState
                rowStoreName = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormStoreName
                rowProductName = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormProductName
                rowOption = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormOption
                rowOrderPriceValue = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormOrderPriceValue
                rowProductPriceValue = rowOrderCheckFormSellerBinding.textViewRowOrderCheckFormProductPriceValue
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCheckFormViewHolder {
            val rowOrderCheckFormCustomerBinding = RowOrderCheckFormSellerBinding.inflate(layoutInflater)
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
}