package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail

import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.carousel.CarouselLayoutManager
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerQnADetailBinding
import com.test.keepgardeningproject_customer.databinding.RowQnaImageBinding


class MyPageCustomerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerQnADetailViewModel

    lateinit var binding: FragmentMyPageCustomerQnADetailBinding
    lateinit var mainActivity: MainActivity

    var qnaIdx:Long = 0
    var uriList = mutableListOf<Uri>()
    private val handler = Handler()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerQnADetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        qnaIdx = arguments?.getLong("qnaIdx", 0)!!

        viewModel = ViewModelProvider(mainActivity)[MyPageCustomerQnADetailViewModel::class.java]
        viewModel.run {

            qnaTitle.observe(mainActivity) {
                binding.editTextViewQcDetailTitle.setText(it)
            }
            qnaContent.observe(mainActivity) {
                binding.editTextViewQcDetailContent.setText(it)
            }
            qnaDate.observe(mainActivity) {
                binding.textviewQcDate.text = it
            }
            qnaAnswer.observe(mainActivity) {
                if(it == "None") {
                    binding.textviewQcDetailReplyState.run {
                        text = "미답변"
                        setTextColor(resources.getColor(R.color.Red))
                    }
                    binding.editTextViewQcDetailAnswer.visibility = View.GONE
                }
                else {
                    binding.textviewQcDetailReplyState.run {
                        text = "답변 완료"
                        setTextColor(resources.getColor(R.color.colorAccent3))
                    }
                    binding.editTextViewQcDetailAnswer.run {
                        visibility = View.VISIBLE
                        setText(it)
                    }
                }
            }
            viewModel.getQnAInfo(qnaIdx)
        }


        binding.run {

            materialToolbarQcDetail.run{

                title = "문의 세부 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    var oldFragment = arguments?.getString("oldFragment")

                    if(oldFragment == "ProductCustomerQnAWriteFragment") {
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.PRODUCT_CUSTOMER_QNA_WRITE_FRAGMENT)
                    } else {
                        mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT)
                    }

                }

            }

            editTextViewQcDetailTitle.run {
                isEnabled = false
                setTextColor(Color.BLACK)
            }
            editTextViewQcDetailContent.run {
                isEnabled = false
                setTextColor(Color.BLACK)
            }
            editTextViewQcDetailAnswer.run {
                isEnabled = false
                setTextColor(Color.BLACK)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.getQnAInfo(qnaIdx)
    }
}