package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowPcddBinding

class ProductCustomerDetailFragment : Fragment() {

    lateinit var fragmentProductCustomerDetailBinding: FragmentProductCustomerDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var productCustomerDetailDetailFragment : ProductCustomerDetailDetailFragment
    lateinit var productCustomerDetailReviewFragment : ProductCustomerDetailReviewFragment

    // 탭
    val tabName = arrayOf(
        "상세정보", "리뷰"
    )

    val fragmentList = mutableListOf<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductCustomerDetailBinding = FragmentProductCustomerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        productCustomerDetailDetailFragment = ProductCustomerDetailDetailFragment()
        productCustomerDetailReviewFragment = ProductCustomerDetailReviewFragment()

        fragmentProductCustomerDetailBinding.run{
            // 툴바
            toolbarPcd.run{
                title = "상품상세정보"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT)
                }
            }

            // 구매완료 버튼
            buttonPcdBuy.setOnClickListener{
                val bs = ProductCustomerDetailBottomDialog()
                bs.show(mainActivity.supportFragmentManager,"구매")
            }

            recyclerViewPcd.run{
                adapter = RecyclerAdapterPCDD()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentProductCustomerDetailBinding.root
    }


    inner class RecyclerAdapterPCDD : RecyclerView.Adapter<RecyclerAdapterPCDD.ViewHolderPCDD>(){
        inner class ViewHolderPCDD(rowPcddBinding: RowPcddBinding) : RecyclerView.ViewHolder(rowPcddBinding.root){
            val imagePcddRow : ImageView

            init{
                imagePcddRow = rowPcddBinding.imagePcddRow
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPCDD {
            val rowPcddBinding = RowPcddBinding.inflate(layoutInflater)
            val viewHolderPCDD = ViewHolderPCDD(rowPcddBinding)

            return viewHolderPCDD
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ViewHolderPCDD, position: Int) {
            holder.imagePcddRow.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}