package com.test.keepgardeningproject_customer.UI.ProductCustomerQnAWrite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerQnaWriteBinding


class ProductCustomerQnAWriteFragment : Fragment() {


    lateinit var productCustomerQnAWriteBinding : FragmentProductCustomerQnaWriteBinding
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
      productCustomerQnAWriteBinding = FragmentProductCustomerQnaWriteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        productCustomerQnAWriteBinding.run {

            materialToolbarPcqWrite.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT)
                }
                setTitle("문의 작성")
            }

            buttonPcqWrite.run {
                setOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT)
                }
            }


        }



        return productCustomerQnAWriteBinding.root
    }


}