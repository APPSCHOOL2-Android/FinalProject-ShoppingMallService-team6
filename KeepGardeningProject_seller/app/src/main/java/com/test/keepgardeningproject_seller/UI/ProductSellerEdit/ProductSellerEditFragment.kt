package com.test.keepgardeningproject_seller.UI.ProductSellerEdit

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment.Companion.productIdx
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainViewModel
import com.test.keepgardeningproject_seller.UI.ProductSellerRegister.ProductSellerRegisterFragment
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerEditBinding
import com.test.keepgardeningproject_seller.databinding.RowSellerRegisterBinding
import java.text.DecimalFormat

class ProductSellerEditFragment : Fragment() {

    lateinit var fragmentProductSellerEditBinding: FragmentProductSellerEditBinding
    lateinit var mainActivity: MainActivity

    lateinit var productSellerEditViewModel: ProductSellerEditViewModel

    var uriList = mutableListOf<Uri>()
    var fileNameList = mutableListOf<String>()
    var imageList = ArrayList<String>()

    val MAX_IMAGE_NUM = 3

    companion object {
        var originImageNum = 0
        fun newInstance() = ProductSellerEditFragment()
    }

    private lateinit var viewModel: ProductSellerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerEditBinding = FragmentProductSellerEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        productSellerEditViewModel = ViewModelProvider(mainActivity)[ProductSellerEditViewModel::class.java]
        productSellerEditViewModel.run {

            productName.observe(mainActivity) {
                fragmentProductSellerEditBinding.textInputEditTextProductSellerEditProductName.setText(it)
            }
            productPrice.observe(mainActivity) {
                fragmentProductSellerEditBinding.textInputEditTextProductSellerEditProductPrice.setText(it)
            }
            productDetail.observe(mainActivity) {
                fragmentProductSellerEditBinding.textInputEditTextProductSellerEditProductDetail.setText(it)
            }
            productCategory.observe(mainActivity) {
                fragmentProductSellerEditBinding.textInputEditTextProductSellerEditCategory.setText(it)
            }
            productImageNameList.observe(mainActivity) {
                fileNameList = it
                fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter?.notifyDataSetChanged()
            }
            productImageUriList.observe(mainActivity) {
                uriList = it
                fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter?.notifyDataSetChanged()
            }
            productSellerEditViewModel.getProductInfo(ProductSellerMainFragment.productIdx.toLong())
        }

        fragmentProductSellerEditBinding.run {

            toolbarProductSellerEdit.run {
                title = "상품 수정"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_SELLER_EDIT_FRAGMENT)
                }
            }

            recyclerViewProductSellerEditImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

                adapter?.notifyDataSetChanged()
            }

            val sheetBehavior = BottomSheetBehavior.from(includeProductSellerEdit.bottomSheetCategory)

            sheetBehavior.isHideable = true
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            textInputEditTextProductSellerEditCategory.setOnTouchListener { v, event ->
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }

            includeProductSellerEdit.run {
                buttonCategoryBottomSheetPlant.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetPlant.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetOrchid.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetOrchid.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetMonstera.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetMonstera.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetBonsai.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetBonsai.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetCommodity.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetCommodity.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetSeed.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetSeed.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetBouquet.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetBouquet.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                buttonCategoryBottomSheetPot.setOnClickListener {
                    textInputEditTextProductSellerEditCategory.setText("${buttonCategoryBottomSheetPot.text}")
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }

            buttonProductSellerEditEdit.setOnClickListener {

                var productName = textInputEditTextProductSellerEditProductName.text.toString()
                var productPrice = textInputEditTextProductSellerEditProductPrice.text.toString()
                var productContent = textInputEditTextProductSellerEditProductDetail.text.toString()
                var productCategory = textInputEditTextProductSellerEditCategory.text.toString()

                if(productName.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 이름을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerEditProductName)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(productPrice.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 가격을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerEditProductPrice)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(productContent.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상세 내용을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerEditProductDetail)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(productCategory.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("카테고리를 선택해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    builder.show()

                    return@setOnClickListener
                }
                mainActivity.removeFragment(PRODUCT_SELLER_EDIT_FRAGMENT)
            }
        }
        return fragmentProductSellerEditBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        var adapter = fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowSellerRegisterBinding: RowSellerRegisterBinding) : RecyclerView.ViewHolder(rowSellerRegisterBinding.root) {

            var imageViewProduct : ImageView

            init {
                imageViewProduct = rowSellerRegisterBinding.imageViewRowSellerRegister

                // context 메뉴 구성 (context 메뉴 활성화)
                rowSellerRegisterBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->

                    mainActivity.menuInflater.inflate(R.menu.delete_menu, menu)

                    // context menu의 항목 선택시 실행되는 함수
                    menu[0].setOnMenuItemClickListener {

                        uriList.removeAt(adapterPosition)
                        fileNameList.removeAt(adapterPosition)
                        originImageNum--

                        // recyclerView 갱신
                        this@RecyclerAdapterClass.notifyDataSetChanged()

                        false
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowSellerRegisterBinding.inflate(layoutInflater)
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