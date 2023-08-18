package com.test.keepgardeningproject_seller.UI.ProductSellerRegister

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerRegisterBinding

class ProductSellerRegisterFragment : Fragment() {

    lateinit var fragmentProductSellerRegisterBinding: FragmentProductSellerRegisterBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = ProductSellerRegisterFragment()
    }

    private lateinit var viewModel: ProductSellerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerRegisterBinding = FragmentProductSellerRegisterBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductSellerRegisterBinding.run {
            val sheetBehavior = BottomSheetBehavior.from(includeProductSellerRegister.bottomSheetCategory)

            sheetBehavior.isHideable = true
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            textInputEditTextProductSellerRegisterCategory.setOnTouchListener { v, event ->
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }

            includeProductSellerRegister.run {
                buttonCategoryBottomSheetPlant.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetPlant.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetOrchid.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetOrchid.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetMonstera.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetMonstera.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetBonsai.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetBonsai.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetCommodity.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetCommodity.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetSeed.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetSeed.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetBouquet.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetBouquet.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetPot.setOnClickListener {
                    textInputEditTextProductSellerRegisterCategory.setText("${buttonCategoryBottomSheetPot.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }


        return fragmentProductSellerRegisterBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}