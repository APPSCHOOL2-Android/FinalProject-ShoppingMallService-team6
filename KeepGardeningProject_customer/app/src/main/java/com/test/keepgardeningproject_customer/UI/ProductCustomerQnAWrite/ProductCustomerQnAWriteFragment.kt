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
    var oldFragment = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
      fragmentProductCustomerQnaWriteBinding = FragmentProductCustomerQnaWriteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        oldFragment = arguments?.getString("oldFragment").toString()
        if(oldFragment == "Product") {
            productIdx = arguments?.getLong("productIdx", 0)!!
        } else {
            productIdx = arguments?.getLong("auctionIdx", 0)!!
        }

        viewModel = ViewModelProvider(mainActivity)[ProductCustomerQnAWriteViewModel::class.java]
        viewModel.run {

            if(oldFragment == "Product") {
                productName.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.textviewPcqWriteProductName.text = it
                }
                productStoreName.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.textviewPcqWriteStoreName.text = it
                }
                productStoreIdx.observe(mainActivity) {
                    storeIdx = it
                }
                productMainImage.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.imageviewPcqImage.setImageBitmap(it)
                }
                viewModel.getProductInfo(productIdx)
            }
            else {
                auctionProductName.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.textviewPcqWriteProductName.text = it
                }
                auctionProductStoreName.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.textviewPcqWriteStoreName.text = it
                }
                auctionProductStoreIdx.observe(mainActivity) {
                    storeIdx = it
                }
                auctionProductMainImage.observe(mainActivity) {
                    fragmentProductCustomerQnaWriteBinding.imageviewPcqImage.setImageBitmap(it)
                }
                viewModel.getAuctionProductInfo(productIdx)
            }
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

            buttonPcqWrite.setOnClickListener {

                var qnaTitle = editTextViewPcqTitle.text.toString()
                var qnaContent = editTextViewPcqContent.text.toString()

                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                val currentMonth = calendar.get(Calendar.MONTH) + 1
                val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                var qnaDate = "$currentYear.$currentMonth.$currentDayOfMonth"

                if(qnaTitle.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("문의 제목을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(editTextViewPcqTitle)
                    }
                    builder.show()

                    return@setOnClickListener
                }
                if(qnaContent.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("문의 내용을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(editTextViewPcqContent)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                QnARepository.getQnAIdx {
                    var qnaIdx = it.result.value as Long
                    // 상품 인덱스 증가
                    qnaIdx++


                    val qnaDataClass:QnAClass = if(oldFragment == "Product") {
                        QnAClass(
                            qnaIdx,
                            "상품",
                            productIdx,
                            MainActivity.loginedUserInfo.userIdx!!.toLong(),
                            storeIdx,
                            qnaTitle,
                            qnaContent,
                            "None",
                            qnaDate
                        )
                    } else {
                        QnAClass(
                            qnaIdx,
                            "경매",
                            productIdx,
                            MainActivity.loginedUserInfo.userIdx!!.toLong(),
                            storeIdx,
                            qnaTitle,
                            qnaContent,
                            "None",
                            qnaDate
                        )
                    }

                    // 상품 정보 저장
                    QnARepository.addQnAInfo(qnaDataClass) {
                        // 상품 인덱스 저장
                        QnARepository.setQnAIdx(qnaIdx) {

                            val newBundle = Bundle()
                            newBundle.putString("oldFragment", "ProductCustomerQnAWriteFragment")
                            newBundle.putLong("qnaIdx", qnaIdx)
                            mainActivity.replaceFragment(MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT, true, newBundle)
                        }
                    }
                }
                Snackbar.make(fragmentProductCustomerQnaWriteBinding.root, "문의가 등록되었습니다.", Snackbar.LENGTH_SHORT).show()

            }

        }
        return fragmentProductCustomerQnaWriteBinding.root
    }

    override fun onResume() {
        super.onResume()
    }
}