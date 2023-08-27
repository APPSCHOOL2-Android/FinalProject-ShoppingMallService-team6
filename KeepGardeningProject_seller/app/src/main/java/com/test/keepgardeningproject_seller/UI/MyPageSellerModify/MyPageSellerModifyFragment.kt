package com.test.keepgardeningproject_seller.UI.MyPageSellerModify


import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository

import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerModifyBinding

class MyPageSellerModifyFragment : Fragment() {

    lateinit var myPageSellerModifyBinding: FragmentMyPageSellerModifyBinding
    lateinit var mainActivity: MainActivity

    lateinit var albumLauncher: ActivityResultLauncher<Intent>


    var uploadUri: Uri? = null
    private lateinit var myPageSellerModifyViewModel: MyPageSellerModifyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        myPageSellerModifyBinding = FragmentMyPageSellerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        albumLauncher = albumSetting(myPageSellerModifyBinding.imageViewMsStoreImg)
        myPageSellerModifyViewModel =
            ViewModelProvider(mainActivity)[MyPageSellerModifyViewModel::class.java]
        savedInstanceState
        myPageSellerModifyViewModel.run {
            newPasswordData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsPassword.setText(it)
            }
            newPasswordCheckData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsPasswordCheck.setText(it)
            }
            newNickNameData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsNickName.setText(it)
            }
            newBannerData.observe(mainActivity) {
                myPageSellerModifyBinding.imageViewMsStoreImg.setImageBitmap(it)

            }
            newStoreNameData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsStoreName.setText(it)
            }
            newStoreDetailData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsStoreDetail.setText(it)
            }
            newAddressNumberData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsAddressNumber.setText(it)
            }
            newAddressDetailData.observe(mainActivity) {
                myPageSellerModifyBinding.textInputEditTextMsAddressDetail.setText(it)
            }

        }


        myPageSellerModifyBinding.run {

            var newclass = mainActivity.loginSellerInfo

            toolbarMs.run {
                setTitle("정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                }
            }
            buttonMcModifySearch.run {
                setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT, true, null)
                }

            }
            imageViewMsStoreImg.run {
                setOnClickListener {
                    val newIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    newIntent.setType("image/*")
                    val mimeType = arrayOf("image/*")
                    newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                    albumLauncher.launch(newIntent)
                    return@setOnClickListener
                }

            }
            buttonMsModifyEnd.run {
                setOnClickListener {

                    //수정할 데이터
                    var idx = newclass.userSellerIdx
                    var logintypes = newclass.userSellerLoginType
                    var emails = newclass.userSellerEmail
                    var newNick = textInputEditTextMsNickName.text.toString()
                    var newPostNum = textInputEditTextMsAddressNumber.text.toString()
                    var newPostDetails = textInputEditTextMsAddressDetail.text.toString()
                    var newStorename = textInputEditTextMsStoreName.text.toString()
                    var newStoredetail = textInputEditTextMsStoreDetail.text.toString()

                    val fileName = if (uploadUri == null) {
                        "None"
                    } else {
                        "image/img_${System.currentTimeMillis()}.jpg"
                    }
                    //이메일
                    if (logintypes == 0L) {

                        var newpw = textInputEditTextMsPassword.text.toString()

                        //유효성 검사
                        if (textInputEditTextMsPassword.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPassword)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsPasswordCheck.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호 확인을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPasswordCheck)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsNickName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsNickName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 이름을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 설명을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressNumber.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("우편주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressNumber)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상세주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsPassword.text.toString() != textInputEditTextMsPasswordCheck.text.toString()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 다시 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPasswordCheck)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }


                        var myclass = UserSellerInfo(
                            idx, logintypes, emails, newpw, newNick, fileName,
                            newStorename, newStoredetail, newPostNum, newPostDetails
                        )
                        UserRepository.modifyUserSellerInfo(myclass) {

                            if (uploadUri != null) {
                                UserRepository.uploadImage(fileName, uploadUri!!) {
                                    myPageSellerModifyViewModel.reset()
                                    Snackbar.make(myPageSellerModifyBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                }
                            } else {
                                UserRepository.uploadImage(fileName, uploadUri!!) {
                                    myPageSellerModifyViewModel.reset()
                                    Snackbar.make(myPageSellerModifyBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    //카카오
                    if (logintypes == 1L) {

                        textInputEditTextMsPassword.visibility = View.GONE
                        textInputEditTextMsPasswordCheck.visibility = View.GONE
                        // 비밀번호를 비설정으로 None으로 표시
                        var newpw = "None"

                        //유효성 검사
                        if (textInputEditTextMsNickName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsNickName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 이름을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 설명을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressNumber.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("우편주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressNumber)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상세주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsPassword.text.toString() != textInputEditTextMsPasswordCheck.text.toString()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 다시 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPasswordCheck)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }


                        var myclass1 = UserSellerInfo(
                            idx,
                            logintypes,
                            emails,
                            newpw,
                            newNick,
                            fileName,
                            newStorename,
                            newStoredetail,
                            newPostNum,
                            newPostDetails
                        )

                        UserRepository.modifyUserSellerInfo(myclass1) {
                            UserRepository.uploadImage(fileName, uploadUri!!) {
                                myPageSellerModifyViewModel.reset()
                                mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                            }
                        }
                    }
                    //네이버
                    if (logintypes == 2L) {
                        textInputEditTextMsPassword.visibility = View.GONE
                        textInputEditTextMsPasswordCheck.visibility = View.GONE

                        var newpw = "None"

                        //유효성 검사
                        if (textInputEditTextMsNickName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsNickName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 이름을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 설명을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressNumber.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("우편주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressNumber)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상세주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsPassword.text.toString() != textInputEditTextMsPasswordCheck.text.toString()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 다시 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPasswordCheck)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var myclass2 = UserSellerInfo(
                            idx,
                            logintypes,
                            emails,
                            newpw,
                            newNick,
                            fileName,
                            newStorename,
                            newStoredetail,
                            newPostNum,
                            newPostDetails
                        )

                        UserRepository.modifyUserSellerInfo(myclass2) {
                            UserRepository.uploadImage(fileName, uploadUri!!) {
                                myPageSellerModifyViewModel.reset()
                                mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                            }
                        }

                    }
                    //구글
                    if (logintypes == 3L) {
                        textInputEditTextMsPassword.visibility = View.GONE
                        textInputEditTextMsPasswordCheck.visibility = View.GONE

                        var newpw = "None"

                        //유효성 검사
                        if (textInputEditTextMsNickName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsNickName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreName.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 이름을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreName)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsStoreDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상점 설명을 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsStoreDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressNumber.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("우편주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressNumber)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsAddressDetail.text!!.isEmpty()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("상세주소를 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsAddressDetail)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }
                        if (textInputEditTextMsPassword.text.toString() != textInputEditTextMsPasswordCheck.text.toString()) {
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setTitle("경고")
                            builder.setMessage("비밀번호를 다시 입력해주세요")
                            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.showSoftInput(textInputEditTextMsPasswordCheck)
                            }
                            builder.setNegativeButton("취소", null)
                            builder.show()
                            return@setOnClickListener
                        }

                        var myclass3 = UserSellerInfo(
                            idx,
                            logintypes,
                            emails,
                            newpw,
                            newNick,
                            fileName,
                            newStorename,
                            newStoredetail,
                            newPostNum,
                            newPostDetails
                        )

                        UserRepository.modifyUserSellerInfo(myclass3) {
                            UserRepository.uploadImage(fileName, uploadUri!!) {
                                myPageSellerModifyViewModel.reset()
                                mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                            }
                        }
                    }


                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                }
            }
        }

        return myPageSellerModifyBinding.root
    }

    fun albumSetting(selectimage: ImageView): ActivityResultLauncher<Intent> {
        val albumContract = ActivityResultContracts.StartActivityForResult()
        val albumLauncher = registerForActivityResult(albumContract) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                if (it.data?.data != null) {
                    uploadUri = it.data?.data
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val source =
                            ImageDecoder.createSource(mainActivity.contentResolver, uploadUri!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        selectimage.setImageBitmap(bitmap)
                    } else {
                        val cursor =
                            mainActivity.contentResolver.query(uploadUri!!, null, null, null, null)
                        if (cursor != null) {
                            cursor.moveToNext()
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            val bitmap = BitmapFactory.decodeFile(source)
                            selectimage.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
        return albumLauncher
    }

    override fun onResume() {
        super.onResume()
        myPageSellerModifyBinding.imageViewMsStoreImg.run {
            setOnClickListener {
                val newIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                newIntent.setType("image/*")
                val mimeType = arrayOf("image/*")
                newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                albumLauncher.launch(newIntent)
            }
        }
        myPageSellerModifyBinding.textInputEditTextMsAddressNumber.setText(mainActivity.postAddress)

    }


}