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
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerEditBinding
import com.test.keepgardeningproject_seller.databinding.RowSellerRegisterBinding

class ProductSellerEditFragment : Fragment() {

    lateinit var fragmentProductSellerEditBinding: FragmentProductSellerEditBinding
    lateinit var mainActivity: MainActivity

    lateinit var productSellerEditViewModel: ProductSellerEditViewModel

    var uriList = mutableListOf<Uri>()
    var fileNameList = mutableListOf<String>()
    var imageList = ArrayList<String>()

    val MAX_IMAGE_NUM = 3

    lateinit var dialog: AlertDialog
    val dialogAutoDismissTime = 3000L // 3초 후에 다이얼로그 닫힘

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

            buttonProductSellerEditAddImage.setOnClickListener {
                if (uriList.count() == MAX_IMAGE_NUM) {
                    Snackbar.make(fragmentProductSellerEditBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                registerForActivityResult.launch(intent)

                var adapter = fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter as RecyclerAdapterClass
                adapter.notifyDataSetChanged()
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


                Snackbar.make(fragmentProductSellerEditBinding.root, "상품 정보가 수정되었습니다.", Snackbar.LENGTH_SHORT).show()

                for (i in 0 until uriList.count()) {
                    // 상품 정보 저장
                    val fileName = if(i<originImageNum) {
                        fileNameList[i]
                    } else {
                        "image/img_${System.currentTimeMillis()}_$i.jpg"
                    }

                    imageList.add(fileName)
                }

                val productDataClass = ProductClass(
                    productIdx.toLong(),
                    imageList,
                    productName,
                    productPrice,
                    mainActivity.loginSellerInfo.userSellerIdx,
                    productCategory,
                    productContent
                )

                // 상품 정보 저장
                ProductRepository.modifyProduct(productDataClass) {

                }
                for (i in 0 until uriList.count()) {
                    // 이미지 업로드
                    ProductRepository.uploadImage(uriList[i]!!, imageList[i]) {

                    }
                }

                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setMessage("LOADING...")
                dialog = builder.create()
                dialog.show()

                // 일정 시간 후에 다이얼로그 닫기
                val handler = Handler()
                handler.postDelayed({
                    dialog.dismiss()
                    mainActivity.removeFragment(PRODUCT_SELLER_EDIT_FRAGMENT)
                }, dialogAutoDismissTime)

            }
        }
        return fragmentProductSellerEditBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerEditViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        var adapter = fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter as RecyclerAdapterClass
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
                            Snackbar.make(fragmentProductSellerEditBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
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
                    var adapter = fragmentProductSellerEditBinding.recyclerViewProductSellerEditImage.adapter as RecyclerAdapterClass
                    adapter.notifyDataSetChanged()
                }
            }
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