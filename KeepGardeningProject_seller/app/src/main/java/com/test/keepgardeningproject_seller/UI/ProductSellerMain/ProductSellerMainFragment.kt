package com.test.keepgardeningproject_seller.UI.ProductSellerMain

import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_MAIN_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.UI.HomeSeller.HomeSellerViewModel
import com.test.keepgardeningproject_seller.UI.ProductSellerDetail.ProductSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerMainBinding
import java.text.DecimalFormat


class ProductSellerMainFragment : Fragment() {

    lateinit var fragmentProductSellerMainBinding: FragmentProductSellerMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var productSellerMainViewModel: ProductSellerMainViewModel

    var fileNameList = mutableListOf<String>()

    companion object {
        var productIdx = 0
        fun newInstance() = ProductSellerMainFragment()
    }

    private lateinit var viewModel: ProductSellerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        productIdx = arguments?.getInt("productIdx", 0)!!

        fragmentProductSellerMainBinding = FragmentProductSellerMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        productSellerMainViewModel = ViewModelProvider(mainActivity)[ProductSellerMainViewModel::class.java]
        productSellerMainViewModel.run {

            productName.observe(mainActivity) {
                fragmentProductSellerMainBinding.textViewProductSellerMainProductName.text = it
            }
            productPrice.observe(mainActivity) {
                // 숫자 comma 표시하기
                var decimal = DecimalFormat("#,###")
                var temp = it.toInt()
                fragmentProductSellerMainBinding.textViewProductSellerMainProductPrice.text = decimal.format(temp) + "원"
            }
            productCategory.observe(mainActivity) {
                fragmentProductSellerMainBinding.textViewProductSellerMainCategory.text = "카테고리 > $it"
            }
            productImageNameList.observe(mainActivity) {
                fileNameList = it
            }
            productMainImage.observe(mainActivity) {
                fragmentProductSellerMainBinding.imageViewProductSellerMainMainImage.setImageBitmap(it)
            }
            productSellerMainViewModel.getProductInfo(productIdx.toLong())
        }

        fragmentProductSellerMainBinding.run {

            toolbarProductSellerMain.run {
                title = "상품 상세 정보"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    var oldFragment = arguments?.getString("oldFragment")
                    if(oldFragment == "ProductSellerRegisterFragment") {
                        mainActivity.removeFragment(PRODUCT_SELLER_REGISTER_FRAGMENT)
                        mainActivity.removeFragment(PRODUCT_SELLER_MAIN_FRAGMENT)
                    } else {
                        mainActivity.removeFragment(PRODUCT_SELLER_MAIN_FRAGMENT)
                    }
                }
            }

            textViewProductSellerMainProdcutSellerName.text = mainActivity.loginSellerInfo.userSellerStoreName

            tabLayoutProductSellerMain.run {
                //ViewPager2 Adapter 셋팅
                var viewPager2Adatper = ViewPager2Adapter(mainActivity)
                viewPager2Adatper.addFragment(ProductSellerDetailFragment())
                viewPager2Adatper.addFragment(ProductSellerReviewFragment())
                viewPager2Adatper.addFragment(ProductSellerQnAFragment())

                //Adapter 연결
                fragmentProductSellerMainBinding.viewPagerProductSellerMainFragment.apply {

                    adapter = viewPager2Adatper


                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    })
                }

                //ViewPager, TabLayout 연결
                TabLayoutMediator(
                    tabLayoutProductSellerMain,
                    viewPagerProductSellerMainFragment
                ) { tab, position ->
                    when (position) {
                        0 -> tab.text = "상세정보"
                        1 -> tab.text = "리뷰"
                        2 -> tab.text = "문의"
                    }
                }.attach()

                buttonProductSellerMainEdit.setOnClickListener {
                    mainActivity.replaceFragment(PRODUCT_SELLER_EDIT_FRAGMENT, true, null)
                }

                buttonProductSellerMainDelete.setOnClickListener {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("해당 상품을 삭제하시겠습니까?")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                        Snackbar.make(fragmentProductSellerMainBinding.root, "해당 상품이 삭제되었습니다.",Snackbar.LENGTH_LONG).show()

                        // 글 삭제
                        ProductRepository.removeProduct(productIdx.toLong()) {
                            // 이미지가 있는 경우 삭제
                            for(i in 0 until fileNameList.size) {
                                ProductRepository.removeImage(fileNameList[i]!!) {}
                            }
                        }
                        SystemClock.sleep(500)
                        mainActivity.removeFragment(PRODUCT_SELLER_MAIN_FRAGMENT)
                    }
                    builder.show()
                }

            }


            return fragmentProductSellerMainBinding.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        fragmentProductSellerMainBinding.viewPagerProductSellerMainFragment.requestLayout()

        productSellerMainViewModel.getProductInfo(productIdx.toLong())
    }
}

class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
        //TODO: notifyItemInserted!!
    }

    fun removeFragement() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
        //TODO: notifyItemRemoved!!
    }

}