package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerQnADetailBinding
import java.time.LocalDate

class MyPageCustomerQnADetailFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnADetailFragment()
    }

    private lateinit var viewModel: MyPageCustomerQnADetailViewModel

    lateinit var binding:FragmentMyPageCustomerQnADetailBinding

    lateinit var mainActivity: MainActivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerQnADetailBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = binding.root

        binding.run{

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

            val now = LocalDate.now()

            textviewQcCurrentTime.text = now.toString()

            val imageId = arguments?.getInt("contentImage")

            imageId?.let{

                imageviewQcDetail.setImageResource(it)

            }

            editTextViewQcDetailTitle.hint = arguments?.getString("contentTitle")

            editTextViewQcDetailContent.hint = arguments?.getString("contentQnA")

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerQnADetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}