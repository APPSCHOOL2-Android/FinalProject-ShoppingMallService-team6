package com.test.keepgardeningproject_seller.UI.HomeSeller

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.ALERT_SELLER_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.HomeSellerMyPageMain.HomeSellerMyPageMainFragment
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerBinding

class HomeSellerFragment : Fragment() {

    lateinit var fragmentHomeSellerBinding: FragmentHomeSellerBinding
    lateinit var mainActivity: MainActivity

    var oldBottom = ""


    companion object {
        fun newInstance() = HomeSellerFragment()

        val HOME_SELLER_MY_PAGE_MAIN_FRAGMENT = "HomeSellerMyPageMainFragment"
        val HOME_SELLER_TAB_FRAGMENT = "HomeSellerTabFragment"
    }

    private lateinit var viewModel: HomeSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeSellerBinding = FragmentHomeSellerBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentHomeSellerBinding.run {

            toolbarHomeSeller.run{

                inflateMenu(R.menu.main_menu)

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_mainMenu_notification -> {
                            mainActivity.replaceFragment(ALERT_SELLER_FRAGMENT, true, null)
                        }
                        else -> { }
                    }
                    true
                }
            }

            bottomNavigationViewHomeSeller.run {

                selectedItemId = R.id.item_bottomMenu_home




                setOnItemSelectedListener {
                    when(it.itemId) {
                        R.id.item_bottomMenu_home -> {
                            replaceFragment(HOME_SELLER_TAB_FRAGMENT, true, false)
                            oldBottom = "home"
                        }
                        R.id.item_bottomMenu_registerProduct -> {
                            val builder = MaterialAlertDialogBuilder(mainActivity)
                            builder.setMessage("어떤 상품을 등록하시겠습니까?")
                            builder.setNegativeButton("일반 상품") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.replaceFragment(PRODUCT_SELLER_REGISTER_FRAGMENT,true,null)
                            }
                            builder.setPositiveButton("경매 상품") { dialogInterface: DialogInterface, i: Int ->
                                mainActivity.replaceFragment(AUCTION_SELLER_REGISTER_FRAGMENT,true,null)
                            }
                            builder.setOnCancelListener {
                                selectedItemId = R.id.item_bottomMenu_home
                            }
                            builder.show()
                            oldBottom = "home"
                        }
                        R.id.item_bottomMenu_mypage -> {
                            replaceFragment(HOME_SELLER_MY_PAGE_MAIN_FRAGMENT, true, false)
                            oldBottom = "mypage"
                        }
                    }
                    true
                }
            }
        }

        return fragmentHomeSellerBinding.root
    }

    override fun onResume() {
        super.onResume()

        if(oldBottom == "mypage") {
            fragmentHomeSellerBinding.bottomNavigationViewHomeSeller.selectedItemId = R.id.item_bottomMenu_mypage
        }
        else {
            fragmentHomeSellerBinding.bottomNavigationViewHomeSeller.selectedItemId = R.id.item_bottomMenu_home
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeSellerViewModel::class.java)
    }

    fun replaceFragment(name: String, addToBackStack: Boolean, animate: Boolean) {
        // Fragment 교체 상태로 설정
        val fragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()

        // 새로운 Fragment를 담을 변수
        var newFragment = when (name) {
            HOME_SELLER_TAB_FRAGMENT -> HomeSellerTabFragment()
            HOME_SELLER_MY_PAGE_MAIN_FRAGMENT -> HomeSellerMyPageMainFragment()
            else -> {
                Fragment()
            }
        }

        if (newFragment != null) {
            // Fragment 교체
            fragmentTransaction.replace(R.id.fragmentContainerView_homeSeller, newFragment)

            if (animate == true) {
                // 애니메이션 설정
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // 이전으로 돌아가는 기능 이용하기 위해 Fragment Backstack에 넣어주기
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령 동작
            fragmentTransaction.commit()
        }
    }

    fun removeFragment(name: String) {
        mainActivity.supportFragmentManager.popBackStack(name, 0)
    }
}