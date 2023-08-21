package com.test.keepgardeningproject_customer.UI.OrderFormCustomer

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentOrderFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderFormCustomerBinding
import kotlin.concurrent.thread

class OrderFormCustomerFragment : Fragment() {
    lateinit var fragmentOrderFormCustomerBinding: FragmentOrderFormCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var viewModel: OrderFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
                next()

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
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return orderFormViewHolder
        }

        override fun getItemCount(): Int {
            return 2
        }

        override fun onBindViewHolder(holder: OrderFormViewHolder, position: Int) {

        }
    }

    fun next() {
        fragmentOrderFormCustomerBinding.run {
            val ordererName = editTextOrderFormOrdererName.text.toString()
            val ordererPhone = editTextOrderFormOrdererPhone.text.toString()
            val ordererEmail = editTextOrderFormOrdererEmail.text.toString()
            val receiverName = editTextOrderFormReceiverName.text.toString()
            val addressPhone = editTextOrderFormAddressPhone.text.toString()
            val zipCode = editTextOrderFormZipCode.text.toString()
            val detailAddress = editTextOrderFormDetailAddress.text.toString()
            val paymentMethod = radioGroupOrderFormPayment.checkedRadioButtonId
            val paymentRadio1 = radioButtonOrderFormDeposit.isChecked
            val paymentRadio2 = radioButtonOrderFormCard.isChecked
            val paymentRadio3 = radioButtonOrderFormNaverPay.isChecked
            val agree1 = checkBoxOrderFormAgree1.isChecked
            var agree2 = checkBoxOrderFormAgree2.isChecked

            if (ordererName.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("주문자명 오류")
                builder.setMessage("주문자명을 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormOrdererName)
                }
                builder.show()
                return
            }

            if (ordererPhone.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("주문자 연락처 오류")
                builder.setMessage("주문자 연락처를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormOrdererPhone)
                }
                builder.show()
                return
            }

            if (ordererEmail.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("주문자 이메일 오류")
                builder.setMessage("주문자 이메일을 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormOrdererEmail)
                }
                builder.show()
                return
            }

            if (receiverName.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("배송지 받는사람 오류")
                builder.setMessage("배송지 받는사람을 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormReceiverName)
                }
                builder.show()
                return
            }

            if (addressPhone.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("배송지 연락처 오류")
                builder.setMessage("배송지 연락처을 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormAddressPhone)
                }
                builder.show()
                return
            }

            if (zipCode.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("우편번호 오류")
                builder.setMessage("우편번호를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormZipCode)
                }
                builder.show()
                return
            }

            if (detailAddress.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("상세주소 오류")
                builder.setMessage("상세주소를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormDetailAddress)
                }
                builder.show()
                return
            }

            if (paymentMethod != R.id.radioButton_orderForm_deposit && paymentMethod != R.id.radioButton_orderForm_card && paymentMethod != R.id.radioButton_orderForm_naverPay) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("결제수단 오류")
                builder.setMessage("결제수단을 선택해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    radioGroupOrderFormPayment.requestFocus()
                }
                builder.show()
                return
            }

            if (agree1 == false || agree2 == false) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("필수 동의 오류")
                builder.setMessage("필수 동의사항에 동의해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    checkBoxOrderFormAllAgree.requestFocus()
                }
                builder.show()
                return
            }

            mainActivity.replaceFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT, true, null)
        }
    }
}