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

    lateinit var viewModel: ProductCustomerQnAWriteViewModel

    var imageList = ArrayList<String>()
    var uriList = ArrayList<Uri>()

    val MAX_IMAGE_NUM = 3

    var productIdx: Long = 0
    var storeIdx: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
      fragmentProductCustomerQnaWriteBinding = FragmentProductCustomerQnaWriteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        productIdx = arguments?.getLong("productIdx", 0)!!

        viewModel = ViewModelProvider(mainActivity)[ProductCustomerQnAWriteViewModel::class.java]
        viewModel.run {

            productName.observe(mainActivity) {
                fragmentProductCustomerQnaWriteBinding.textviewPcqWriteProductName.text = it
            }
            productStoreName.observe(mainActivity) {
                fragmentProductCustomerQnaWriteBinding.textviewPcqWriteStoreName.text = it
            }
            productStoreIdx.observe(mainActivity) {
                storeIdx = it
//                fragmentProductCustomerQnaWriteBinding.recyclerViewPcqImage.adapter?.notifyDataSetChanged()
            }
            productMainImage.observe(mainActivity) {
                fragmentProductCustomerQnaWriteBinding.imageviewPcqImage.setImageBitmap(it)
            }
            viewModel.getProductInfo(productIdx)
        }

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
            recyclerViewPcqImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }

    override fun onResume() {
        super.onResume()

        var adapter = fragmentProductCustomerQnaWriteBinding.recyclerViewPcqImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowRegisterImageBinding: RowRegisterImageBinding) : RecyclerView.ViewHolder(rowRegisterImageBinding.root) {

            var imageViewProduct : ImageView

            init {
                imageViewProduct = rowRegisterImageBinding.imageViewRowSellerRegister

                // context 메뉴 구성 (context 메뉴 활성화)
                rowRegisterImageBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->

                    mainActivity.menuInflater.inflate(R.menu.menu_delete_image, menu)

                    // context menu의 항목 선택시 실행되는 함수
                    menu[0].setOnMenuItemClickListener {

                        uriList.removeAt(adapterPosition)

                        // recyclerView 갱신
                        this@RecyclerAdapterClass.notifyDataSetChanged()

                        false
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowRegisterImageBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            return viewHolder
        }

        override fun getItemCount(): Int {
            return uriList.count()
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            Glide.with(mainActivity).load(uriList[position]).into(holder.imageViewProduct)
        }
    }


}