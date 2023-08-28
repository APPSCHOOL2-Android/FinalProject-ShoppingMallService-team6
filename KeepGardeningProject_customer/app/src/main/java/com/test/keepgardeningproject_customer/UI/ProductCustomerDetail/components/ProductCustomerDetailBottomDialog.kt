package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailFragment
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailViewModel
import com.test.keepgardeningproject_customer.databinding.DialogPcdBinding
import java.text.DecimalFormat

class ProductCustomerDetailBottomDialog : BottomSheetDialogFragment() {

    lateinit var dialogPcdBinding: DialogPcdBinding
    lateinit var productCustomerDetailFragment: ProductCustomerDetailFragment
    lateinit var productCustomerDetailViewModel: ProductCustomerDetailViewModel
    lateinit var mainActivity: MainActivity

    // 선택할 상품 갯수
    var count = 1
    var price = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogPcdBinding = DialogPcdBinding.inflate(inflater)
        productCustomerDetailFragment = ProductCustomerDetailFragment()
        mainActivity = activity as MainActivity

        // 뷰모델 실행
        productCustomerDetailViewModel = ViewModelProvider(mainActivity)[ProductCustomerDetailViewModel::class.java]
        productCustomerDetailViewModel.getProductInfoByIdx(productCustomerDetailFragment.idx.toDouble())
        productCustomerDetailViewModel.run{
            productInfo.observe(mainActivity){
                price = it.productPrice?.toInt()!!

                // 가격표시
                val dec = DecimalFormat("#,###")
                val temp = dec.format(price)
                dialogPcdBinding.textViewPcdDialogPrice.text = temp + " 원"
            }
        }

        dialogPcdBinding.run{
            // 클릭시 숫자 조절
            textViewPcdNumber.text = count.toString()
            imagePcdDialogPlus.setOnClickListener {
                count ++
                textViewPcdNumber.text = count.toString()

                // 가격 반영
                val dec = DecimalFormat("#,###")
                val temp = dec.format(price * count)
                textViewPcdDialogPrice.text = temp + " 원"
            }
            imagePcdDialogMinus.setOnClickListener {
                count = if(count > 1) count-1 else 1
                textViewPcdNumber.text = count.toString()

                // 가격 반영
                val dec = DecimalFormat("#,###")
                val temp = dec.format(price * count)
                textViewPcdDialogPrice.text = temp + " 원"
            }

            // 문의하기
            buttonPcdDialogInquiry.setOnClickListener {

            }

            // 장바구니
            buttonPcdDialogBuy.setOnClickListener{

            }

            // 구매하기
            buttonPcdDialogBuy.setOnClickListener{
                var bundle = Bundle()
                // 선택한 상품 productIdx 전달
                bundle.putLong("productIdx", productCustomerDetailFragment.idx)
                bundle.putInt("count",count)
                mainActivity.replaceFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT,true,bundle)
                
                // 다이얼로그 없애기ㅡ
                dismiss()
            }

        }

        return dialogPcdBinding.root
    }
}