package com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_customer.DAO.Review
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ReviewRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerReviewWriteBinding

class MyPageCustomerReviewWrite : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerReviewWrite()
    }

    private lateinit var viewModel: MyPageCustomerReviewWriteViewModel

    lateinit var binding:FragmentMyPageCustomerReviewWriteBinding

    lateinit var mainActivity: MainActivity

    var imageList = ArrayList<String>()
    var uriList = ArrayList<Uri>()

    val MAX_IMAGE_NUM = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerReviewWriteBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

            materialToolbarRcWrite.run{

                title = "리뷰내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)

                }

            }

            buttonRcWrite.run{

                setOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)

                }

            }

            //사용자 인덱스 추출
            val userIdx = MainActivity.loginedUserInfo.userIdx.toString()


            //제품 이름 송신(bundle)

            val productIdx = arguments?.getLong("ordersProductIdx")

            //리뷰 분기할 인덱스 송신(bundle)

            val ordersIdx = arguments?.getLong("ordersIdx")


            //코드 작업 순서
            //1.productName과 productStoreIdx를 얻어온다.
            //2.productStoreIdx를 통해 userSellerStoreName에 접근, 상점명을 얻어온다.
            //3.제품명, 상점명을 알았으니, review객체를 생성해서 데이터베이스에 업로드 한다.

            buttonAuctionSellerEditAddImage.setOnClickListener{

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)


            }

            buttonRcWrite.run {
                setOnClickListener {

                    for (i in 0 until uriList.count()) {
                        // 경매 상품 정보 저장
                        val fileName = "image/img_${System.currentTimeMillis()}_$i.jpg"

                        imageList.add(fileName)
                    }

                    //입력요소에 대한 유효성 검사.

                    if (editTextViewRcTitle.text!!.isEmpty()) {

                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("입력오류")
                        builder.setMessage("리뷰제목을 입력하세요")
                        builder.setIcon(R.drawable.ic_close_24px)
                        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                            editTextViewRcTitle.requestFocus()
                        }
                        builder.show()

                    } else if (editTextViewRcContent.text!!.isEmpty()) {

                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("입력 오류")
                        builder.setMessage("리뷰를 입력하세요")
                        builder.setIcon(R.drawable.ic_close_24px)
                        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                            editTextViewRcContent.requestFocus()
                        }
                        builder.show()

                    } else {

                        ReviewRepository.getProductNameByProductIdx(productIdx!!) {

                            for (c1 in it.result.children) {

                                val productName = c1.child("productName").value as String

                                val storeIdx = c1.child("productStoreIdx").value as Long

                                ReviewRepository.getStoreNameByStoreIdx(storeIdx) {

                                    for (c2 in it.result.children) {

                                        val storeName =
                                            c2.child("userSellerStoreName").value as String

                                        val review: Review = Review(
                                            userIdx,
                                            ordersIdx.toString(),
                                            productIdx,
                                            productName,
                                            storeName,
                                            imageList,
                                            ratingbarRcReviewDetail.rating,
                                            editTextViewRcTitle.text.toString(),
                                            editTextViewRcContent.text.toString()
                                        )

                                        ReviewRepository.uploadReview(userIdx, review)

                                    }

                                }

                            }


                        }

                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT)


                    }


                }
            }
            
            viewModel =
                ViewModelProvider(mainActivity)[MyPageCustomerReviewWriteViewModel::class.java]


        }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerReviewWriteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}