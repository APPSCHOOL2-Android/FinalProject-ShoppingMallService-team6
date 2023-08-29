package com.test.keepgardeningproject_seller.UI.MyPageSellerModify


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.UserInfo
import com.test.keepgardeningproject_seller.DAO.UserDAO
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionSellerDetailRepository
import com.test.keepgardeningproject_seller.Repository.UserRepository

import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerModifyBinding
import java.net.URL

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
        myPageSellerModifyViewModel = ViewModelProvider(this).get(MyPageSellerModifyViewModel::class.java)
        myPageSellerModifyBinding.run {
            var userInfo = mainActivity.loginSellerInfo
            textInputEditTextMsNickName.setText(userInfo.userSellerNickname)
            textInputEditTextMsAddressNumber.setText(userInfo.userSellerPostNumber)
            textInputEditTextMsAddressDetail.setText(userInfo.userSellerPostDetail)
            textInputEditTextMsStoreName.setText(userInfo.userSellerStoreName)
            textInputEditTextMsStoreDetail.setText(userInfo.userSellerStoreInfo)
            val fileName = if (uploadUri == null) "None" else "image/img_${System.currentTimeMillis()}.jpg"
            buttonMcModifySearch.run {
                setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT, true, null)
                }
            }
            buttonMsModifyEnd.setOnClickListener {
                var nickname = textInputEditTextMsNickName.text.toString()
                var addressNumber = textInputEditTextMsAddressNumber.text.toString()
                var addressDetail = textInputEditTextMsAddressDetail.text.toString()
                var storeName = textInputEditTextMsStoreName.text.toString()
                var storeDetail = textInputEditTextMsStoreDetail.text.toString()
                var userInfoTemp = UserSellerInfo(userInfo.userSellerIdx,userInfo.userSellerLoginType,userInfo.userSellerEmail,userInfo.userSellerPw,nickname,userInfo.userSellerBanner,storeName,storeDetail, addressNumber,addressDetail)
                UserRepository.modifyUserSellerInfo(userInfoTemp) {
                    if (uploadUri != null) {
                        UserRepository.uploadImage(fileName, uploadUri!!) {
                            myPageSellerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageSellerModifyBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                        }
                    } else {
                        UserRepository.uploadImage(fileName, uploadUri!!) {
                            myPageSellerModifyViewModel.reset()
                            mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                            Snackbar.make(myPageSellerModifyBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                        }
                    }

                    mainActivity.loginSellerInfo = userInfoTemp
                }
                mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
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
        myPageSellerModifyBinding.run {
            var userInfo = mainActivity.loginSellerInfo
            textInputEditTextMsNickName.setText(userInfo.userSellerNickname)
            textInputEditTextMsAddressNumber.setText(userInfo.userSellerPostNumber)
            textInputEditTextMsAddressDetail.setText(userInfo.userSellerPostDetail)
            textInputEditTextMsStoreName.setText(userInfo.userSellerStoreName)
            textInputEditTextMsStoreDetail.setText(userInfo.userSellerStoreInfo)
        }
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