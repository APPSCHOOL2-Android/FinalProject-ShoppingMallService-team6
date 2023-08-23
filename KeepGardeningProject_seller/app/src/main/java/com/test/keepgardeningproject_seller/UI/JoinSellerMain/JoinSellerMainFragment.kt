package com.test.keepgardeningproject_seller.UI.JoinSellerMain

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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.UserSellerInfo
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.UserRepository
import com.test.keepgardeningproject_seller.databinding.FragmentJoinSellerMainBinding

class JoinSellerMainFragment : Fragment() {
    lateinit var fragmentJoinSellerMainBinding: FragmentJoinSellerMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 업로드할 이미지의 Uri
    var uploadUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentJoinSellerMainBinding = FragmentJoinSellerMainBinding.inflate(inflater)

        // 앨범 설정
        albumLauncher = albumSetting(fragmentJoinSellerMainBinding.imageViewJoinSellerMain)

        fragmentJoinSellerMainBinding.run {
            toolbarJoinSellerMain.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.JOIN_SELLER_MAIN_FRAGMENT)
                }
                setTitle("회원가입 하기")
            }


            // 이메일 포커스 주기
            textInputLayoutJoinSellerMainEmail.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    var emailCheck = textInputLayoutJoinSellerMainEmail.editText?.text.toString()
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (emailCheck.isEmpty()) {
                        textInputLayoutJoinSellerMainEmail.error = "이메일을 입력해주세요"
                    } else if (!emailCheck.matches(emailPattern.toRegex())) {
                        textInputLayoutJoinSellerMainEmail.error = "이메일 형식이 잘못되었습니다."
                    } else {
                        textInputLayoutJoinSellerMainEmail.error = null
                        textInputLayoutJoinSellerMainEmail.isErrorEnabled = false
                    }
                }
            }
            // 중복확인하기
            buttonJoinSellerMainDoubleCheck.setOnClickListener {
                Toast.makeText(requireContext(), "중복확인", Toast.LENGTH_SHORT).show()
            }

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

            // 로그인 화면으로
            buttonJoinSellerMainJoin.setOnClickListener {
                userSubmit()

            }
        }
        return fragmentJoinSellerMainBinding.root
    }

    fun userSubmit() {
        fragmentJoinSellerMainBinding.run {

            //이메일,패스워드,닉네임,배너,상점명, 상점설명, 우편번호, 상세주소
            var email = textInputEditTextJoinSellerMainEmail.text.toString()
            var pw = textInputEditTextJoinSellerMainPassword.text.toString()
            var nickNames = textInputEditTextJoinSellerMainNickName.text.toString()
            var storeName = textInputEditTextJoinSellerMainStoreName.text.toString()
            var storeInfo = textInputEditTextJoinSellerMainStoreDetail.text.toString()
            var postNumber = textInputEditTextJoinSellerMainPostNumber.text.toString()
            var postDetail = textInputEditTextJoinSellerMainStoreAddress.text.toString()

            UserRepository.getUserSellerIndex {
                var userindex = it.result.value as Long

                userindex++

                // 배너 이미지 선택 안하면 파일 이름은 None으로 설정
                val fileName = if (uploadUri == null) {
                    "None"
                } else {
                    "image/img_${System.currentTimeMillis()}.jpg"
                }

                val userinfo =
                    UserSellerInfo(userindex, 0, email, pw, nickNames, fileName, storeName, storeInfo, postNumber, postDetail)

                UserRepository.setUserSellerInfo(userinfo) {
                    UserRepository.setUserSellerIdx(userindex) {
                        // 이미지 업로드
                        if (uploadUri != null) {
                            UserRepository.uploadStoreImage(uploadUri!!, fileName) {
                                Snackbar.make(fragmentJoinSellerMainBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT)
                                    .show()
                                mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                                mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
                            }
                        } else {
                            Snackbar.make(fragmentJoinSellerMainBinding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                            mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
                            mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
                        }
//                        mainActivity.removeFragment(MainActivity.LOGIN_SELLER_TO_EMAIL_FRAGMENT)
//                        mainActivity.removeFragment(MainActivity.LOGIN_SELLER_MAIN_FRAGMENT)
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