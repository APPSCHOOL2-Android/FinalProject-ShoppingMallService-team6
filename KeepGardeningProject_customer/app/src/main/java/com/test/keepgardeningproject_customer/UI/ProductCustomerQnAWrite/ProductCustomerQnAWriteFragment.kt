package com.test.keepgardeningproject_customer.UI.ProductCustomerQnAWrite

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_customer.DAO.QnAClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.MainActivity.Companion.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.QnARepository
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerQnaWriteBinding
import com.test.keepgardeningproject_customer.databinding.RowRegisterImageBinding
import java.text.DecimalFormat
import java.util.Calendar
import kotlin.concurrent.fixedRateTimer


class ProductCustomerQnAWriteFragment : Fragment() {


    lateinit var fragmentProductCustomerQnaWriteBinding: FragmentProductCustomerQnaWriteBinding
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
      fragmentProductCustomerQnaWriteBinding = FragmentProductCustomerQnaWriteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        productCustomerQnAWriteBinding.run {
        fragmentProductCustomerQnaWriteBinding.run {

            materialToolbarPcqWrite.run {
                title = "문의 작성"
                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT)
                }
            }

                }
            }


        }



        return productCustomerQnAWriteBinding.root
    }


}