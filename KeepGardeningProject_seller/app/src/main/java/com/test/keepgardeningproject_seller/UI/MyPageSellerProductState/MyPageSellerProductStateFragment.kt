package com.test.keepgardeningproject_seller.UI.MyPageSellerProductState

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.DAO.ProductState
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerProductStateBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageProductStateBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerQuaBinding
import org.w3c.dom.Text

class MyPageSellerProductStateFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerProductStateFragment()
    }

    private lateinit var viewModel: MyPageSellerProductStateViewModel

    lateinit var binding: FragmentMyPageSellerProductStateBinding

    lateinit var mainActivity: MainActivity

    var ProductState: ProductState = ProductState("결제완료")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerProductStateBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run {

            materialToolbarSellerProductStateBar.run {

                title = "판매/배송"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT)

                }

            }

            recyclerViewSellerProductState.run {

                adapter = ProductStateRecyclerViewAdapter(ProductState)

                layoutManager = LinearLayoutManager(context)

            }

            buttonSellerProductPaid.run {

                setOnClickListener {

                    val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                    buttonSellerProductPaid.backgroundTintList =
                        ColorStateList.valueOf(colorPrimary)

                    buttonSellerProductReady.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductDelivery.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductDelivered.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)


                    recyclerViewSellerProductState.run {

                        val ProductState: ProductState = ProductState("결제완료")

                        adapter = ProductStateRecyclerViewAdapter(ProductState)

                        adapter?.notifyDataSetChanged()

                        layoutManager = LinearLayoutManager(context)
                    }

                }

            }
            buttonSellerProductReady.run {

                setOnClickListener {

                    val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                    buttonSellerProductReady.backgroundTintList =
                        ColorStateList.valueOf(colorPrimary)

                    buttonSellerProductPaid.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductDelivery.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductDelivered.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)

                    recyclerViewSellerProductState.run {

                        val ProductState: ProductState = ProductState("상품준비")

                        adapter = ProductStateRecyclerViewAdapter(ProductState)

                        adapter?.notifyDataSetChanged()

                        layoutManager = LinearLayoutManager(context)
                    }
                }

            }

            buttonSellerProductDelivery.run {

                setOnClickListener {

                    //클릭한 버튼을 제외한 버튼들 전부 회색 처리

                    val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                    buttonSellerProductDelivery.backgroundTintList =
                        ColorStateList.valueOf(colorPrimary)

                    buttonSellerProductPaid.backgroundTintList = ColorStateList.valueOf(Color.GRAY)

                    buttonSellerProductReady.backgroundTintList = ColorStateList.valueOf(Color.GRAY)

                    buttonSellerProductDelivered.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)

                    recyclerViewSellerProductState.run {

                        val ProductState: ProductState = ProductState("배송중")

                        adapter = ProductStateRecyclerViewAdapter(ProductState)

                        adapter?.notifyDataSetChanged()

                        layoutManager = LinearLayoutManager(context)
                    }

                }

            }

            buttonSellerProductDelivered.run {

                setOnClickListener {

                    val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
                    buttonSellerProductDelivered.backgroundTintList =
                        ColorStateList.valueOf(colorPrimary)

                    buttonSellerProductPaid.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductReady.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    buttonSellerProductDelivery.backgroundTintList =
                        ColorStateList.valueOf(Color.GRAY)

                    recyclerViewSellerProductState.run {

                        val ProductState: ProductState = ProductState("배송완료")

                        adapter = ProductStateRecyclerViewAdapter(ProductState)

                        adapter?.notifyDataSetChanged()

                        layoutManager = LinearLayoutManager(context)
                    }

                }

            }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerProductStateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class ProductStateRecyclerViewAdapter(val productState: ProductState) :
        RecyclerView.Adapter<ProductStateRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(productStateBinding: RowMyPageProductStateBinding) :
            RecyclerView.ViewHolder(productStateBinding.root) {

            val productState: TextView
            val orderNumber: TextView
            val productName: TextView
            val productPrice: TextView
            val productCount: TextView

            val orderConfirmBtn: ImageButton

            init {
                productState = productStateBinding.textViewSellerProductState
                orderNumber = productStateBinding.textviewSellerProductOrderNumber
                productName = productStateBinding.textviewSellerProductName
                productPrice = productStateBinding.sellerPrice
                productCount = productStateBinding.sellerCount

                orderConfirmBtn = productStateBinding.imagebuttonSellerOrderConfirm


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val rowProductStateBinding = RowMyPageProductStateBinding.inflate(layoutInflater)
            val ViewHolder = ReviewViewHolder(rowProductStateBinding)

            rowProductStateBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            holder.productState.text = productState.state
            holder.orderNumber.text = "19-70013831"
            holder.productPrice.text = "10000"

            holder.orderConfirmBtn.setOnClickListener {
                mainActivity.replaceFragment(
                    MainActivity.ORDER_CHECK_FORM_SELLER_FRAGMENT,
                    true,
                    null
                )
            }


        }
    }

}