package com.test.keepgardeningproject_customer.UI.CartCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.databinding.FragmentCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowCartCustomerBinding
import java.text.DecimalFormat

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
            cartCount.observe(mainActivity) {
                fragmentCartCustomerBinding.textViewCartCount.text = "전체 ${it}개"
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

            buttonCartDeleteAll.setOnClickListener {
                cartCustomerViewModel.deleteAllCart(userIdx!!)
            }

            buttonCartPay.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("fromWhere", "cartPage")
                mainActivity.replaceFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT, true, bundle)
            }

            // 현재 사용자의 장바구니 정보를 가져온다.
            cartCustomerViewModel.getProductInCart(userIdx)
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

                rowCartCustomerBinding.buttonRowCartDelete.setOnClickListener {
                    val cartClass = cartCustomerViewModel.cartList.value?.get(adapterPosition)!!
                    cartCustomerViewModel.removeCartProduct(cartClass)
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

            // 가격 표시 형식 맞추기
            var decimal = DecimalFormat("#,###")
            var price = cartCustomerViewModel.cartList.value?.get(position)?.cartPrice?.toInt()
            holder.rowPriceValue.text = decimal.format(price) + "원"

            holder.rowCountValue.text = cartCustomerViewModel.cartList.value?.get(position)?.cartCount.toString()
        }
    }
}