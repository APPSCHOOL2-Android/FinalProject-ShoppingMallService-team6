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

            buttonPcqAddImage.setOnClickListener {
                if (uriList.count() == MAX_IMAGE_NUM) {
                    Snackbar.make(fragmentProductCustomerQnaWriteBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                registerForActivityResult.launch(intent)

                var adapter = fragmentProductCustomerQnaWriteBinding.recyclerViewPcqImage.adapter as RecyclerAdapterClass
                adapter.notifyDataSetChanged()
            }

            recyclerViewPcqImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
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

                if(uriList.size == 0) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("이미지를 1개 이상 선택해주세요.")
                    builder.setPositiveButton("확인",null)
                    builder.show()

                    return@setOnClickListener
                }

                QnARepository.getQnAIdx {
                    var qnaIdx = it.result.value as Long
                    // 상품 인덱스 증가
                    qnaIdx++

                    for (i in 0 until uriList.count()) {
                        // 상품 정보 저장
                        val fileName = "image/img_${System.currentTimeMillis()}_$i.jpg"

                        imageList.add(fileName)
                    }

                    val qnaDataClass = QnAClass(qnaIdx, "상품", productIdx, MainActivity.loginedUserInfo.userIdx!!.toLong(), storeIdx, qnaTitle, qnaContent, "None", qnaDate, imageList)

                    // 상품 정보 저장
                    QnARepository.addQnAInfo(qnaDataClass) {
                        // 상품 인덱스 저장
                        QnARepository.setQnAIdx(qnaIdx) {

                            for (i in 0 until uriList.count()) {
                                // 이미지 업로드
                                QnARepository.uploadImage(uriList[i]!!, imageList[i]) {

                                }
                            }

                            SystemClock.sleep(3000)
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

        var adapter = fragmentProductCustomerQnaWriteBinding.recyclerViewPcqImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val clipData = result.data?.clipData
                    if (clipData != null) { // 이미지를 여러 개 선택할 경우
                        val clipDataSize = clipData.itemCount
                        val selectableCount = MAX_IMAGE_NUM - uriList.count()
                        if (clipDataSize > selectableCount) { // 최대 선택 가능한 개수를 초과해서 선택한 경우
                            Snackbar.make(fragmentProductCustomerQnaWriteBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
                        } else {
                            // 선택 가능한 경우 ArrayList에 가져온 uri를 넣어준다.
                            for (i in 0 until clipDataSize) {
                                uriList.add(clipData.getItemAt(i).uri)
                            }
                        }
                    } else { // 이미지를 한 개만 선택할 경우 null이 올 수 있다.
                        val uri = result?.data?.data
                        if (uri != null) {
                            uriList.add(uri)
                        }
                    }
                    var adapter = fragmentProductCustomerQnaWriteBinding.recyclerViewPcqImage.adapter as RecyclerAdapterClass
                    adapter.notifyDataSetChanged()
                }
            }
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