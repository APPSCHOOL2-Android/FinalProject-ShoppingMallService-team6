package com.test.keepgardeningproject_seller.UI.MyPageSellerModify


import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
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
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        myPageSellerModifyBinding = FragmentMyPageSellerModifyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        albumLauncher = albumSetting(myPageSellerModifyBinding.imageViewMsStoreImg)

        myPageSellerModifyBinding.run {
            toolbarMs.run {
                setTitle("내 정보 수정")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                }
            }
            var userInfo = mainActivity.loginSellerInfo
            textInputEditTextMsNickName.setText(userInfo.userSellerNickname)
            textInputEditTextMsAddressNumber.setText(userInfo.userSellerPostNumber)
            textInputEditTextMsAddressDetail.setText(userInfo.userSellerPostDetail)
            textInputEditTextMsStoreName.setText(userInfo.userSellerStoreName)
            textInputEditTextMsStoreDetail.setText(userInfo.userSellerStoreInfo)

            buttonMcModifySearch.run {
                setOnClickListener {
                    mainActivity.replaceFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT, true, null)
                }

            }

            imageViewMsStoreImg.setOnClickListener {
                // 앨범에서 사진을 선택할 수 있는 Activity를 실행한다.
                val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // 실행할 액티비티의 마임타입 설정(이미지로 설정해준다)
                newIntent.setType("image/*")
                // 선택할 파일의 타입을 지정(안드로이드  OS가 이미지에 대한 사전 작업을 할 수 있도록)
                val mimeType = arrayOf("image/*")
                newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                // 액티비티를 실행한다.
                albumLauncher.launch(newIntent)

            }
            var userbanner = userInfo.userSellerBanner!!
            Log.e("테스트 합니다.", "$userbanner")
            if (userbanner != "None") { // 이미지 URL이 "None"이 아닐 때에만 이미지 로드
                UserRepository.getBannerImage(userbanner) { result ->
                    if (result.isSuccessful) {
                        val fileUri = result.result
                        Glide.with(mainActivity).load(fileUri).into(imageViewMsStoreImg)
                    } else {
                        Log.e("이미지 로드 실패", result.exception.toString())
                    }
                }
            }

            buttonMsModifyEnd.setOnClickListener {
                var nickname = textInputEditTextMsNickName.text.toString()
                var addressNumber = textInputEditTextMsAddressNumber.text.toString()
                var addressDetail = textInputEditTextMsAddressDetail.text.toString()
                var storeName = textInputEditTextMsStoreName.text.toString()
                var storeDetail = textInputEditTextMsStoreDetail.text.toString()
                val fileName = if (uploadUri == null) "None" else "image/img_${System.currentTimeMillis()}.jpg"
                if (uploadUri != null) {
                    UserRepository.uploadImage(fileName, uploadUri!!) {
                        mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_MODIFY_FRAGMENT)
                    }
                }
                val userInfoTemp = UserSellerInfo(userInfo.userSellerIdx,userInfo.userSellerLoginType,userInfo.userSellerEmail,userInfo.userSellerPw,nickname,fileName,storeName,storeDetail, addressNumber,addressDetail)
                UserRepository.modifyUserSellerInfo(userInfoTemp) {
                    if(it.isSuccessful){
                        mainActivity.loginSellerInfo = userInfoTemp
                    }
                }
                mainActivity.loginSellerInfo = userInfoTemp
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


}