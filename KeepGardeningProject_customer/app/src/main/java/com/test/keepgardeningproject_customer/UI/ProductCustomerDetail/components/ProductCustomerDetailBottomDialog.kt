package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.CartClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.Repository.CartRepository
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


                // 다이얼로그 없애기ㅡ
                dismiss()
            }

            // 장바구니
            buttonPcdDialogCart.setOnClickListener{
                // 로그인 안되어 있는경우
                if(MainActivity.isLogined == false){
                    mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT,true,null)
                }
                // 로그인 되어있는 경우
                else{
                    CartRepository.getCartIdx {
                        var cartIdx = it.result.value as Long
                        cartIdx++

                        val cartClass = CartClass(
                            cartIdx,
                            MainActivity.loginedUserInfo.userIdx!!,
                            productCustomerDetailViewModel.productInfo.value?.productIdx!!,
                            productCustomerDetailViewModel.productInfo.value?.productName!!,
                            (price * count).toLong(),
                            count.toLong(),
                            productCustomerDetailViewModel.productInfo.value?.productImageList?.get(0)!!
                        )

                        CartRepository.addCartInfo(cartClass) {
                            CartRepository.setCartIdx(cartIdx) {
                                Snackbar.make(dialogPcdBinding.root, "장바구니에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
                                dismiss()
                            }
                        }
                    }
                }
            }

            // 구매하기
            buttonPcdDialogBuy.setOnClickListener{
                // 로그인 안되어 있는경우
                if(MainActivity.isLogined == false){
                    mainActivity.replaceFragment(MainActivity.LOGIN_CUSTOMER_MAIN_FRAGMENT,true,null)
                }
                // 로그인 되어있는 경우
                else{
                    var bundle = Bundle()
                    // 선택한 상품 productIdx 전달
                    bundle.putLong("productIdx", MainActivity.chosedProductIdx)
                    bundle.putInt("count",count)
                    bundle.putString("fromWhere", "productPage")
                    mainActivity.replaceFragment(MainActivity.ORDER_FORM_CUSTOMER_FRAGMENT,true,bundle)
                }
                
                // 다이얼로그 없애기ㅡ
                dismiss()
            }

        }

        return dialogPcdBinding.root
    }
}