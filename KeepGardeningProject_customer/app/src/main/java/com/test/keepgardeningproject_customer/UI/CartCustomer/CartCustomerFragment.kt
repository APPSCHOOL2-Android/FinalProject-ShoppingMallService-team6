package com.test.keepgardeningproject_customer.UI.CartCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.databinding.FragmentCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowOrderCheckFormCustomerBinding

class CartCustomerFragment : Fragment() {
    lateinit var fragmentCartCustomerBinding: FragmentCartCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var cartCustomerViewModel: CartCustomerViewModel

    val userIdx = MainActivity.loginedUserInfo.userIdx!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCartCustomerBinding = FragmentCartCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        cartCustomerViewModel = ViewModelProvider(mainActivity)[CartCustomerViewModel::class.java]
        cartCustomerViewModel.run {
            cartList.observe(mainActivity) {
                fragmentCartCustomerBinding.recyclerViewCart.adapter?.notifyDataSetChanged()
            }
            cartImageList.observe(mainActivity) {
                fragmentCartCustomerBinding.recyclerViewCart.adapter?.notifyDataSetChanged()
            }
        }

        fragmentCartCustomerBinding.run {
            toolbarCart.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.CART_CUSTOMER_FRAGMENT)
                }
                title = "장바구니"
            }

            recyclerViewCart.run {
                adapter = CartRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }

            buttonCartPay.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT, true, null)
            }

            // 현재 사용자의 장바구니 정보를 가져온다.
            cartCustomerViewModel.getProductInCart(userIdx?.toDouble()!!)
//            CartRepository.get(userIdx!!.toDouble()) {
//                for (c1 in it.result.children) {
//                    val cartIdx = c1.child("cartIdx").value as Long
//                    val cartUserIdx = c1.child("cartUserIdx").value as Long
//                    val cartProductIdx = c1.child("cartProductIdx").value as MutableList<Long>
//                    val cartCount = c1.child("cartCount").value as Long
//
//                    cartClass = CartClass(cartIdx, cartUserIdx, cartProductIdx, cartCount)
//
//                    // 장바구니에 담겨있는 상품 정보를 가져온다.
//                    viewModel.getProductInCart(cartClass.cartProductIdx)
//                }
//            }
        }

        return fragmentCartCustomerBinding.root
    }

    inner class CartRecyclerViewAdpater : RecyclerView.Adapter<CartRecyclerViewAdpater.CartViewHolder>() {
        inner class CartViewHolder(rowCartCustomerBinding: RowCartCustomerBinding) :
            RecyclerView.ViewHolder(rowCartCustomerBinding.root) {

            var rowProductName: TextView
            var rowPriceValue: TextView
            var rowCountValue: TextView
            var rowProductImage: ImageView

            init {
                rowProductName = rowCartCustomerBinding.textViewRowCartProductName
                rowPriceValue = rowCartCustomerBinding.textViewRowCartPriceValue
                rowCountValue = rowCartCustomerBinding.textViewRowCartCountValue
                rowProductImage = rowCartCustomerBinding.imageViewRowCartProduct

                rowCartCustomerBinding.buttonRowCartMinus.setOnClickListener {
                    val cartClass = cartCustomerViewModel.cartList.value?.get(adapterPosition)!!
                    if (cartClass.cartCount >= 2) {
                        cartCustomerViewModel.minusCartProduct(cartClass)
                    }
                }

                rowCartCustomerBinding.buttonRowCartPlus.setOnClickListener {
                    val cartClass = cartCustomerViewModel.cartList.value?.get(adapterPosition)!!
                    cartCustomerViewModel.plusCartProduct(cartClass)
                }
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
            return cartCustomerViewModel.cartList.value?.size!!
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            var fileName = cartCustomerViewModel.cartImageList.value?.get(position)!!
            CartRepository.getProductMainImage(fileName) {
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.rowProductImage)
            }
            holder.rowProductName.text = cartCustomerViewModel.cartList.value?.get(position)?.cartName
            holder.rowPriceValue.text = "${cartCustomerViewModel.cartList.value?.get(position)?.cartPrice}원"
            holder.rowCountValue.text = cartCustomerViewModel.cartList.value?.get(position)?.cartCount.toString()
        }
    }
}