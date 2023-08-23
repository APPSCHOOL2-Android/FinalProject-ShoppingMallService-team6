package com.test.keepgardeningproject_seller.UI.ProductSellerRegister

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_MAIN_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.StoreClass
import com.test.keepgardeningproject_seller.UserClass
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerRegisterBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductSellerRegisterFragment : Fragment() {

    lateinit var fragmentProductSellerRegisterBinding: FragmentProductSellerRegisterBinding
    lateinit var mainActivity: MainActivity

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null

    lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    var imageList = arrayListOf<String>("None")


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

        // 카메라 설정
        cameraLauncher = cameraSetting(fragmentProductSellerRegisterBinding.imageViewProductSellerRegisterProductImage1)
        // 앨범 설정
        albumLauncher = albumSetting(fragmentProductSellerRegisterBinding.imageViewProductSellerRegisterProductImage1)

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

            buttonProductSellerRegisterAddImage.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setMessage("이미지 등록 방법을 선택해주세요.")
                builder.setNegativeButton("갤러리") { dialogInterface: DialogInterface, i: Int ->
                    val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    newIntent.setType("image/*")
                    val mimeType = arrayOf("image/*")
                    newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                    albumLauncher.launch(newIntent)
                }
                builder.setPositiveButton("카메라") { dialogInterface: DialogInterface, i: Int ->
                    val newIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    // 사진이 저장될 파일 이름
                    val fileName = "/temp_upload.jpg"
                    // 경로
                    val filePath = mainActivity.getExternalFilesDir(null).toString()
                    // 경로 + 파일이름
                    val picPath = "${filePath}/${fileName}"

                    // 사진이 저장될 경로를 관리할 Uri객체 생성
                    // 업로드시 사용할 Uri
                    val file = File(picPath)
                    uploadUri = FileProvider.getUriForFile(mainActivity,
                        "com.test.keepgardeningproject_seller.file_provider", file)

                    newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uploadUri)
                    cameraLauncher.launch(newIntent)
                }
                builder.show()
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

                ProductRepository.getProductIdx {
                    var productIdx = it.result.value as Long
                    // 상품 인덱스 증가
                    productIdx++

                    // 상품 정보 저장
                    val fileName = if(uploadUri == null) {
                        "None"
                    } else {
                        "image/img_${System.currentTimeMillis()}.jpg"
                    }

                    imageList[0] = fileName

                    val productDataClass = ProductClass(productIdx, imageList, productName, productPrice, 1, productCategory, productContent)

                    // 상품 정보 저장
                    ProductRepository.addProductInfo(productDataClass) {
                        // 상품 인덱스 저장
                        ProductRepository.setProductIdx(productIdx) {

                            // 이미지 업로드
                            if(uploadUri != null){
                                ProductRepository.uploadImage(uploadUri!!, fileName) {
                                    Snackbar.make(fragmentProductSellerRegisterBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                    val newBundle = Bundle()
                                    newBundle.putString("oldFragment", "ProductSellerRegisterFragment")
                                    mainActivity.replaceFragment(PRODUCT_SELLER_MAIN_FRAGMENT, true, newBundle)
                                }
                            } else {
                                Snackbar.make(fragmentProductSellerRegisterBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                val newBundle = Bundle()
                                newBundle.putString("oldFragment", "ProductSellerRegisterFragment")
                                mainActivity.replaceFragment(PRODUCT_SELLER_MAIN_FRAGMENT, true, newBundle)
                            }
                        }
                    }
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

    // 카메라 관련 설정
    fun cameraSetting(previewImageView: ImageView) : ActivityResultLauncher<Intent> {
        // 사진 촬영을 위한 런처
        val cameraContract = ActivityResultContracts.StartActivityForResult()
        val cameraLauncher = registerForActivityResult(cameraContract) {
            if(it?.resultCode == AppCompatActivity.RESULT_OK) {
                // Uri를 이용해 이미지에 접근하여 Bitmap 객체 생성
                val bitmap = BitmapFactory.decodeFile(uploadUri?.path)

                // 이미지 크기 조정
                val ratio = 1024.0 / bitmap.width
                val targetHeight = (bitmap.height * ratio).toInt()
                val bitmap2 = Bitmap.createScaledBitmap(bitmap, 1024, targetHeight, false)

                // 회전 각도값
                val degree = getDegree(uploadUri!!)

                // 회전 이미지 생성
                val matrix = Matrix()
                matrix.postRotate(degree.toFloat())
                val bitmap3 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.width, bitmap2.height, matrix, false)
                previewImageView.setImageBitmap(bitmap3)
            }
        }

        return cameraLauncher
    }

    // 이미지 파일에 기록되어 있는 회전 정보를 가져온다.
    fun getDegree(uri:Uri) : Int {

        var exifInterface: ExifInterface? = null

        // 사진 파일로 부터 tag 정보를 관리하는 객체를 추출한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val photoUri = MediaStore.setRequireOriginal(uri)
            // 스트림을 추출한다.
            val inputStream = mainActivity.contentResolver.openInputStream(photoUri)
            // ExifInterface 정보를 읽엉돈다.
            exifInterface = ExifInterface(inputStream!!)
        } else {
            exifInterface = ExifInterface(uri.path!!)
        }

        var degree = 0
        if(exifInterface != null){
            // 각도 값을 가지고온다.
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

            when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        }
        return degree
    }

    // 앨범 관련 설정
    fun albumSetting(previewImageView: ImageView) : ActivityResultLauncher<Intent> {

        val albumContract = ActivityResultContracts.StartActivityForResult()
        val albumLauncher = registerForActivityResult(albumContract) {

            if(it.resultCode == AppCompatActivity.RESULT_OK) {
                // 선택한 이미지에 접근할 수 있는 Uri 객체를 추출한다.
                if(it.data?.data != null){
                    uploadUri = it.data?.data

                    // 안드로이드 10 (Q) 이상이라면...
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        // 이미지를 생성할 수 있는 디코더를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uploadUri!!)
                        // Bitmap객체를 생성한다.
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        previewImageView.setImageBitmap(bitmap)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터 정보를 가져온다.
                        val cursor = mainActivity.contentResolver.query(uploadUri!!, null, null, null, null)
                        if(cursor != null){
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성하여 보여준다.
                            val bitmap = BitmapFactory.decodeFile(source)
                            previewImageView.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }

        return albumLauncher
    }

}