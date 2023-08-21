package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.keepgardeningproject_customer.databinding.DialogPcdBinding

class ProductCustomerDetailBottomDialog : BottomSheetDialogFragment() {

    lateinit var dialogPcdBinding: DialogPcdBinding

    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogPcdBinding = DialogPcdBinding.inflate(inflater)

        dialogPcdBinding.run{

            // 클릭시 숫자 조절
            textViewPcdNumber.text = count.toString()
            imagePcdDialogPlus.setOnClickListener {
                count ++
                textViewPcdNumber.text = count.toString()
            }
            imagePcdDialogMinus.setOnClickListener {
                count = if(count > 0) count-1 else 0
                textViewPcdNumber.text = count.toString()
            }


        }

        return dialogPcdBinding.root
    }
}