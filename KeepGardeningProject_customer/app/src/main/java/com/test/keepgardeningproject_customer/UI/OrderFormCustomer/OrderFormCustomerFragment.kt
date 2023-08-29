package com.test.keepgardeningproject_customer.UI.OrderFormCustomer

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.textfield.TextInputEditText
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.DAO.OrdersProductClass
import com.test.keepgardeningproject_customer.DAO.TotalOrderClass
import com.test.keepgardeningproject_customer.DAO.purchaseInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.Repository.TotalOrderRepository
import com.test.keepgardeningproject_customer.UI.CartCustomer.CartCustomerViewModel
import com.test.keepgardeningproject_customer.databinding.FragmentOrderFormCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderFormCustomerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class OrderFormCustomerFragment : Fragment() {
    lateinit var fragmentOrderFormCustomerBinding: FragmentOrderFormCustomerBinding
    lateinit var mainActivity: MainActivity

    var decimal = DecimalFormat("#,###")

    var address = ""
    val userIdx = MainActivity.loginedUserInfo.userIdx!!
    var totalOrderPrice = 0L
    var selectedPayment = ""


    private lateinit var orderFormCustomerViewModel: OrderFormCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentOrderFormCustomerBinding = FragmentOrderFormCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        val fromWhere = arguments?.getString("fromWhere")!!

        orderFormCustomerViewModel = ViewModelProvider(mainActivity)[OrderFormCustomerViewModel::class.java]
        orderFormCustomerViewModel.run {
            orderFormProductList.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.recyclerViewOrderForm.adapter?.notifyDataSetChanged()
            }
            orderFormProductImageList.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.recyclerViewOrderForm.adapter?.notifyDataSetChanged()
            }
            orderFormTotalProductPrice.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.textViewOrderFormProductPrice.text = decimal.format(it) + "원"
            }
            orderFormDeliveryFee.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.textViewOrderFormDeliveryFee.text = decimal.format(it) + "원"
            }
            orderFormTotalPrice.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.run {
                    textViewOrderFormTotalPaymentPrice.text = decimal.format(it) + "원"
                    textViewOrderFormFinalPayPrice.text = decimal.format(it) + "원"
                }
            }
            orderFormOrdererName.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.editTextOrderFormOrdererName.setText(it)
            }
            orderFormOrdererEmail.observe(mainActivity) {
                fragmentOrderFormCustomerBinding.editTextOrderFormOrdererEmail.setText(it)
            }
        }


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

            editTextOrderFormOrdererName.setTextColor(Color.BLACK)
            editTextOrderFormOrdererEmail.setTextColor(Color.BLACK)
            editTextOrderFormAddress.setTextColor(Color.BLACK)

            buttonOrderFormFindAddress.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT, true, null)
            }

            radioGroupOrderFormPayment.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton = when (checkedId) {
                    R.id.radioButton_orderForm_deposit -> radioButtonOrderFormDeposit
                    R.id.radioButton_orderForm_card -> radioButtonOrderFormCard
                    R.id.radioButton_orderForm_naverPay -> radioButtonOrderFormNaverPay
                    else -> radioButtonOrderFormDeposit
                }
                selectedPayment = selectedRadioButton.text.toString()
            }

            checkBoxOrderFormAllAgree.run {
                setOnCheckedChangeListener { compoundButton, b ->
                    // 각 체크박스를 가지고 있는 레이아웃을 통해 그 안에 있는 View들의 체크상태를 변경한다.
                    for (v1 in checkBoxGroupOrderFormAgree.children) {
                        // 형변환
                        v1 as MaterialCheckBox
                        // 취미 전체가 체크 되어 있다면
                        if (b) {
                            v1.checkedState = MaterialCheckBox.STATE_CHECKED
                        } else {
                            v1.checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                    }
                }
            }

            buttonOrderFormSubmitOrder.setOnClickListener {
                next()
            }

            if (fromWhere == "cartPage") {
                orderFormCustomerViewModel.getProductFromCart(MainActivity.loginedUserInfo.userIdx!!)
            } else if (fromWhere == "productPage") {
                val productIdx = arguments?.getLong("productIdx")!!
                var count = arguments?.getInt("count")!!
                orderFormCustomerViewModel.getProductFromProduct(productIdx, count.toLong())
            }
        }

        return fragmentOrderFormCustomerBinding.root
    }

    inner class OrderFormRecyclerViewAdpater : RecyclerView.Adapter<OrderFormRecyclerViewAdpater.OrderFormViewHolder>() {

        inner class OrderFormViewHolder(rowOrderFormCustomerBinding: RowOrderFormCustomerBinding) :
            RecyclerView.ViewHolder(rowOrderFormCustomerBinding.root) {

            var rowProductName: TextView
            var rowProductPrice: TextView
            var rowProductTotalPrice: TextView
            var rowProductCount: TextView
            var rowProductImage: ImageView

            init {
                rowProductName = rowOrderFormCustomerBinding.textViewRowOrderFormProductName
                rowProductPrice = rowOrderFormCustomerBinding.textViewRowOrderFormProductPrice
                rowProductTotalPrice = rowOrderFormCustomerBinding.textViewRowOrderFormTotalPrice
                rowProductCount = rowOrderFormCustomerBinding.textViewRowOrderFormProductCount
                rowProductImage = rowOrderFormCustomerBinding.imageViewRowOrderFormProductImage
            }
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
            return orderFormCustomerViewModel.orderFormProductList.value?.size!!
        }

        override fun onBindViewHolder(holder: OrderFormViewHolder, position: Int) {
            var fileName = orderFormCustomerViewModel.orderFormProductImageList.value?.get(position)!!
            CartRepository.getProductMainImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowProductImage)
            }
            holder.rowProductName.text = orderFormCustomerViewModel.orderFormProductList.value?.get(position)?.cartName

            var decimal = DecimalFormat("#,###")
            var price1 = orderFormCustomerViewModel.orderFormProductList.value?.get(position)?.cartPrice
            holder.rowProductPrice.text = decimal.format(price1) + "원"

            var price2 = orderFormCustomerViewModel.orderFormProductList.value?.get(position)?.cartPrice!! + 3000
            holder.rowProductTotalPrice.text = decimal.format(price2) + "원"
            totalOrderPrice += price2

            holder.rowProductCount.text = "${orderFormCustomerViewModel.orderFormProductList.value?.get(position)?.cartCount}개"
        }

    }

    fun next() {
        fragmentOrderFormCustomerBinding.run {
            val ordererName = editTextOrderFormOrdererName.text.toString()
            val ordererPhone = editTextOrderFormOrdererPhone.text.toString()
            val ordererEmail = editTextOrderFormOrdererEmail.text.toString()
            val receiverName = editTextOrderFormReceiverName.text.toString()
            val receiverPhone = editTextOrderFormReceiverPhone.text.toString()
            val address = editTextOrderFormAddress.text.toString()
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

            if (receiverPhone.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("배송지 연락처 오류")
                builder.setMessage("배송지 연락처을 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormReceiverPhone)
                }
                builder.show()
                return
            }

            if (address.isEmpty()) {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("우편번호 오류")
                builder.setMessage("우편번호를 입력해주세요")
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(editTextOrderFormAddress)
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

            val coroutineScope = CoroutineScope(Dispatchers.Main)

            // 전체 주문 인덱스 번호를 가져온다.
            TotalOrderRepository.getTotalOrderIdx {
                var totalOrderIdx = it.result.value as Long
                totalOrderIdx++

                // 개별 주문 인덱스 번호를 가져온다.
                OrderProductRepository.getOrdersProductIdx {
                    var ordersIdx = it.result.value as Long
                    val adapter = OrderFormRecyclerViewAdpater()

                    // 주문서에 있는 개별 상품만큼 반복
                    for (product in orderFormCustomerViewModel.orderFormProductList.value!!) {
                        ordersIdx++

                        // 개별 상품의 주문 정보
                        val ordersProductClass = OrdersProductClass(
                            ordersIdx,
                            userIdx,
                            product.cartProductIdx,
                            product.cartCount,
                            product.cartPrice,
                            "결제완료",
                            totalOrderIdx
                        )

                        OrderProductRepository.addOrdersProductInfo(ordersProductClass) {
                            // 개별 주문 인덱스 번호 저장
                            OrderProductRepository.setOrdersProductIdx(ordersIdx)
                        }
                    }
                }

//                for (product in orderFormCustomerViewModel.orderFormProductList.value!!) {
//                    // 개별 주문 인덱스 번호를 가져온다.
//                    OrderProductRepository.getOrdersProductIdx {
//                        var ordersIdx = it.result.value as Long
//                        ordersIdx++
//
//                        // 개별 상품의 주문 정보
//                        val ordersProductClass = OrdersProductClass(
//                            ordersIdx,
//                            userIdx,
//                            product.cartProductIdx,
//                            "",
//                            product.cartCount,
//                            product.cartPrice,
//                            "결제완료",
//                            totalOrderIdx
//                        )
//
//                        OrderProductRepository.addOrdersProductInfo(ordersProductClass) {
//                            // 개별 주문 인덱스 번호 저장
//                            OrderProductRepository.setOrdersProductIdx(ordersIdx)
//                        }
//                    }
//                }

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val orderDate = sdf.format(Date(System.currentTimeMillis()))

                val totalOrderClass = TotalOrderClass(
                    totalOrderIdx,
                    userIdx,
                    orderDate,
                    totalOrderPrice,
                    editTextOrderFormOrdererPhone.text.toString(),
                    editTextOrderFormReceiverName.text.toString(),
                    editTextOrderFormReceiverPhone.text.toString(),
                    editTextOrderFormAddress.text.toString(),
                    editTextOrderFormDetailAddress.text.toString(),
                    editTextOrderFormDeliveryRequest.text.toString(),
                    selectedPayment
                )

                TotalOrderRepository.addTotalOrdertInfo(totalOrderClass) {
                    TotalOrderRepository.setTotalOrderIdx(totalOrderIdx) {
                        val bundle = Bundle()
                        bundle.putLong("totalOrderIdx", totalOrderIdx)
                        mainActivity.replaceFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT, true, bundle)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fragmentOrderFormCustomerBinding.run {
            editTextOrderFormAddress.setText(mainActivity.address)
        }
    }

    fun getData() {
        var useridx = MainActivity.loginedUserInfo.userIdx!!
        PurchaseRepository.getPurchaseIndex {
            //idx2 == purchaseInfoIdx:Long
            var purchaseinfoidx = it.result.value as Long
            purchaseinfoidx++
            //useridx로 결제한 상품을 가져옴
            OrderProductRepository.getIndexorderInfo(useridx) {
                for (c1 in it.result.children) {
                    var newstate = c1.child("ordersDeliveryState").value.toString()
                    var newordersidx = c1.child("ordersIdx").value as Long
                    var newproductidx =  c1.child("ordersProductIdx").value as Long
                    var newtotalorderidx = c1.child("ordersTotalOrderIdx").value as Long
                    ProductRepository.getProductInfoByIdx(newproductidx.toDouble()) {
                        for (c2 in it.result.children) {
                            //결제한 상품의 이름,이미지,상태
                            var newname = c2.child("productName").value.toString()
                            var imglist = c2.child("productImageList").value as ArrayList<String>
                            var newimg = imglist[0]

                            if(newstate== "결제완료"){
                                var newclass = purchaseInfo(newproductidx,newordersidx,useridx,newtotalorderidx,purchaseinfoidx,
                                        newname,newimg,newstate)
                                PurchaseRepository.setPurchaseInfo(newclass) {
                                    PurchaseRepository.setPurchaseIndex(purchaseinfoidx) {
                                        Log.d("Limidx","${newclass}")
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }


    }
}