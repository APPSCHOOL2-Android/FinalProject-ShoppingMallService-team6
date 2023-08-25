package com.test.keepgardeningproject_seller.UI.ProductSellerRegister

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_MAIN_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerRegisterBinding
import com.test.keepgardeningproject_seller.databinding.RowSellerRegisterBinding

class ProductSellerRegisterFragment : Fragment() {

    lateinit var fragmentProductSellerRegisterBinding: FragmentProductSellerRegisterBinding
    lateinit var mainActivity: MainActivity

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null


    var imageList = ArrayList<String>()
    var uriList = ArrayList<Uri>()

    val MAX_IMAGE_NUM = 3

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

            toolbarProductSellerRegister.run {
                title = "상품 등록"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_SELLER_REGISTER_FRAGMENT)
                }
            }

            recyclerViewProductSellerRegisterImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }

            buttonProductSellerRegisterAddImage.setOnClickListener {
                if (uriList.count() == MAX_IMAGE_NUM) {
                    Snackbar.make(fragmentProductSellerRegisterBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                registerForActivityResult.launch(intent)

                var adapter = fragmentProductSellerRegisterBinding.recyclerViewProductSellerRegisterImage.adapter as RecyclerAdapterClass
                adapter.notifyDataSetChanged()
            }

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

            buttonProductSellerRegisterRegister.setOnClickListener {
                var productName = textInputEditTextProductSellerRegisterProductName.text.toString()
                var productPrice = textInputEditTextProductSellerRegisterProductPrice.text.toString()
                var productContent = textInputEditTextProductSellerRegisterProductDetail.text.toString()
                var productCategory = textInputEditTextProductSellerRegisterCategory.text.toString()

                if(productName.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 이름을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerRegisterProductName)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(productPrice.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 가격을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerRegisterProductPrice)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(productContent.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상세 내용을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextProductSellerRegisterProductDetail)
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

                if(uriList.size == 0) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("이미지를 1개 이상 선택해주세요.")
                    builder.setPositiveButton("확인",null)
                    builder.show()

                    return@setOnClickListener
                }

                ProductRepository.getProductIdx {
                    var productIdx = it.result.value as Long
                    // 상품 인덱스 증가
                    productIdx++

                    for (i in 0 until uriList.count()) {
                        // 상품 정보 저장
                        val fileName = "image/img_${System.currentTimeMillis()}_$i.jpg"

                        imageList.add(fileName)
                    }

                    val productDataClass = ProductClass(productIdx, imageList, productName, productPrice, mainActivity.loginSellerInfo.userSellerIdx, productCategory, productContent)

                    // 상품 정보 저장
                    ProductRepository.addProductInfo(productDataClass) {
                        // 상품 인덱스 저장
                        ProductRepository.setProductIdx(productIdx) {

                            for (i in 0 until uriList.count()) {
                                // 이미지 업로드
                                ProductRepository.uploadImage(uriList[i]!!, imageList[i]) {

                                }
                            }

                            val newBundle = Bundle()
                            newBundle.putString(
                                "oldFragment",
                                "ProductSellerRegisterFragment"
                            )
                            newBundle.putInt("productIdx", productIdx.toInt())
                            mainActivity.replaceFragment(PRODUCT_SELLER_MAIN_FRAGMENT, true, newBundle)
                        }
                    }
                }
                Snackbar.make(fragmentProductSellerRegisterBinding.root, "상품이 등록되었습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }


        return fragmentProductSellerRegisterBinding.root
    }

    override fun onResume() {
        super.onResume()
        var adapter = fragmentProductSellerRegisterBinding.recyclerViewProductSellerRegisterImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
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
                            Snackbar.make(fragmentProductSellerRegisterBinding.root, "이미지는 최대 ${MAX_IMAGE_NUM}장까지 첨부할 수 있습니다.", Snackbar.LENGTH_SHORT).show()
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
                    var adapter = fragmentProductSellerRegisterBinding.recyclerViewProductSellerRegisterImage.adapter as RecyclerAdapterClass
                    adapter.notifyDataSetChanged()
                }
            }
        }


    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowSellerRegisterBinding: RowSellerRegisterBinding) : RecyclerView.ViewHolder(rowSellerRegisterBinding.root) {

            var imageViewProduct : ImageView

            init {
                imageViewProduct = rowSellerRegisterBinding.imageViewRowSellerRegister
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
            holder.imageViewProduct.setImageURI(uriList[position])
        }
    }

}