package com.test.keepgardeningproject_seller.UI.MyPageSellerProductState

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    lateinit var binding:FragmentMyPageSellerProductStateBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerProductStateBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarSellerProductStateBar.run{

                title = "판매/배송"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT)

                }

            }

         recyclerViewSellerProductState.run{

             adapter = ProductStateRecyclerViewAdapter()

             layoutManager = LinearLayoutManager(context)

         }

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageSellerProductStateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class ProductStateRecyclerViewAdapter :
        RecyclerView.Adapter<ProductStateRecyclerViewAdapter.ReviewViewHolder>() {
        inner class ReviewViewHolder(productStateBinding: RowMyPageProductStateBinding) :
            RecyclerView.ViewHolder(productStateBinding.root) {

            val productState:TextView
            val orderNumber:TextView
            val productName:TextView
            val productPrice :TextView
            val productCount:TextView

            val stateConvertBtn: Button

            val orderConfirmBtn : ImageButton

            init {
                productState = productStateBinding.textViewSellerProductState
                orderNumber = productStateBinding.textViewSellerOrderNumber
                productName = productStateBinding.textviewSellerProductName
                productPrice = productStateBinding.sellerPrice
                productCount = productStateBinding.sellerCount

                stateConvertBtn = productStateBinding.buttonSellerProductStateConvert

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
            holder.productName.text = "아보카도"



        }
    }

}