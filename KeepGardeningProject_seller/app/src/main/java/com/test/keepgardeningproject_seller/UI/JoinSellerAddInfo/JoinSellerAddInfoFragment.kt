package com.test.keepgardeningproject_seller.UI.JoinSellerAddInfo

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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.API.GoogleAPI
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.API.NaverAPI
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentJoinSellerAddInfoBinding


class JoinSellerAddInfoFragment : Fragment() {
    lateinit var fragmentJoinSellerAddInfoBinding:FragmentJoinSellerAddInfoBinding
    lateinit var mainActivity: MainActivity
    lateinit var albumLauncher: ActivityResultLauncher<Intent>
    var userType:Long = 0

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentJoinSellerAddInfoBinding = FragmentJoinSellerAddInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        // 앨범 설정
        albumLauncher = albumSetting(fragmentJoinSellerAddInfoBinding.imageViewJoinSellerMain)
        var kakaoApi = KakaoAPI()
        val googleAPI = GoogleAPI()
        val naverAPI = NaverAPI()
        fragmentJoinSellerAddInfoBinding.run {
            var joinUserType = arguments?.getLong("joinUserType")
            if (joinUserType != null) {
                userType = joinUserType
            }
            val joinUserEmail = arguments?.getString("joinUserEmail")
            textInputEditTextJoinSellerAddInfoEmail.setText(joinUserEmail)
            imageViewJoinSellerMain.setOnClickListener {
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
            buttonJoinSellerAddInfoJoin.setOnClickListener {
                userSubmit()
                mainActivity.replaceFragment(MainActivity.HOME_SELLER_FRAGMENT,false,null)

            }
            buttonJoinSellerAddInfoFindPostNumber.setOnClickListener {
                googleAPI.googleLogOut(requireContext())
                naverAPI.naverWithdraw()
            }
        }


        return fragmentJoinSellerAddInfoBinding.root
    }



    // join시 firebase에 올리기
    fun userSubmit() {
        fragmentJoinSellerAddInfoBinding.run {

            // 이메일,패스워드,닉네임,배너,상점명, 상점설명, 우편번호, 상세주소
            var email = textInputEditTextJoinSellerAddInfoEmail.text.toString()
            var pw = "None"
            var nickNames = textInputEditTextJoinSellerAddInfoNickName.text.toString()
            var storeName = textInputEditTextJoinSellerAddInfoStoreName.text.toString()
            var storeInfo = textInputEditTextJoinSellerAddInfoStoreDetail.text.toString()
            var postNumber = textInputEditTextJoinSellerAddInfoPostNumber.text.toString()
            var postDetail = textInputEditTextJoinSellerAddInfoStoreAddress.text.toString()

            UserRepository.getUserSellerIndex {
                var userindex = it.result.value as Long
                userindex++
                // 배너 이미지 선택 안하면 파일 이름은 None으로 설정
                val fileName = if (uploadUri == null) {
                    "None"
                } else {
                    "image/img_${System.currentTimeMillis()}.jpg"
                }

                val userinfo = UserSellerInfo(userindex, userType, email, pw, nickNames, fileName, storeName, storeInfo, postNumber, postDetail)

                UserRepository.setUserSellerInfo(userinfo) {
                    UserRepository.setUserSellerIdx(userindex) {
                        // 이미지 업로드
                        if (uploadUri != null) {
                            UserRepository.uploadStoreImage(uploadUri!!, fileName) {
                                //Snackbar.make(fragmentJoinSellerAddInfoBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                mainActivity.removeFragment(MainActivity.JOIN_SELLER_ADD_INFO_FRAGMENT)
                            }
                        } else {
                            //Snackbar.make(fragmentJoinSellerAddInfoBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                            mainActivity.removeFragment(MainActivity.JOIN_SELLER_ADD_INFO_FRAGMENT)
                        }
//
                    }
                }
            }
        }
    }

    // 앨범 관련 설정
    fun albumSetting(previewImageView: ImageView): ActivityResultLauncher<Intent> {
        val albumContract = ActivityResultContracts.StartActivityForResult()
        val albumLauncher = registerForActivityResult(albumContract) {
            if (it?.resultCode == AppCompatActivity.RESULT_OK) {
                // 선택한 이미지에 접근할 수 있는 Uri 객체를 추출한다.
                if (it.data?.data != null) {
                    uploadUri = it.data?.data

                    // 안드로이드 10 (Q) 이상이라면...
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // 이미지를 생성할 수 있는 디코더를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uploadUri!!)
                        // Bitmap객체를 생성한다.
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        previewImageView.setImageBitmap(bitmap)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터 정보를 가져온다.
                        val cursor = mainActivity.contentResolver.query(uploadUri!!, null, null, null, null)
                        if (cursor != null) {
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