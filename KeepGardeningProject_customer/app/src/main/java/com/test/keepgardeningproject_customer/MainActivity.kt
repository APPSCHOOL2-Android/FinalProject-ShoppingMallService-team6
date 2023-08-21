package com.test.keepgardeningproject_customer

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.test.keepgardeningproject_customer.UI.AlertCustomer.AlertCustomerFragment
import com.test.keepgardeningproject_customer.UI.AuctionCustomer.AuctionCustomerFragment
import com.test.keepgardeningproject_customer.UI.AuctionCustomerDetail.AuctionCustomerDetailFragment
import com.test.keepgardeningproject_customer.UI.CartCustomer.CartCustomerFragment
import com.test.keepgardeningproject_customer.UI.HomeCustomerMain.HomeCustomerMainFragment
import com.test.keepgardeningproject_customer.UI.HomeCustomerSearch.HomeCustomerSearchFragment
import com.test.keepgardeningproject_customer.UI.JoinCustomerMain.JoinCustomerMainFragment
import com.test.keepgardeningproject_customer.UI.LoginCustomerFindPw.LoginCustomerFindPwFragment
import com.test.keepgardeningproject_customer.UI.LoginCustomerMain.LoginCustomerMainFragment
import com.test.keepgardeningproject_customer.UI.LoginCustomerToEmail.LoginCustomerToEmailFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerAuction.MyPageCustomerAuctionFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerModify.MyPageCustomerModifyFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase.MyPageCustomerPurchaseFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerQnA.MyPageCustomerQnAFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerQnADetail.MyPageCustomerQnADetailFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerReview.MyPageCustomerReviewFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewDetail.MyPageCustomerReviewDetailFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerReviewWrite.MyPageCustomerReviewWrite
import com.test.keepgardeningproject_customer.UI.MyPageCustomerWish.MyPageCustomerWishFragment
import com.test.keepgardeningproject_customer.UI.OrderCheckFormCustomer.OrderCheckFormCustomerFragment
import com.test.keepgardeningproject_customer.UI.OrderFormCustomer.OrderFormCustomerFragment
import com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.ProductCustomerDetailFragment
import com.test.keepgardeningproject_customer.UI.SearchAddress.SearchAddressFragment
import com.test.keepgardeningproject_customer.UI.StoreInfoCustomer.StoreInfoCustomerFragment
import com.test.keepgardeningproject_customer.UI.StoreInfoCustomerDetail.StoreInfoCustomerDetailFragment
import com.test.keepgardeningproject_customer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    // 다음 주소 검색 api에서 받아온 주소
    var address = ""

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.INTERNET
    )
    companion object{
        val ALERT_CUSTOMER_FRAGMENT = "AlertCustomerFragment"
        val AUCTION_CUSTOMER_FRAGMENT = "AuctionCustomerFragment"
        val AUCTION_CUSTOMER_DETAIL_FRAMGNET = "AuctionCustomerDetailFragment"
        val CART_CUSTOMER_FRAGMENT = "CartCustomerFragment"

        val HOME_CUSTOMER_MAIN_FRAMGNET ="HomeCustomerMainFragment"
        val HOME_CUSTOMER_SEARCH_FRAGMENT = "HomeCustomerSearchFragment"
        val JOIN_CUSTOMER_MAIN_FRAGMENT = "JoinCustomerMainFragment"

        val LOGIN_CUSTOMER_MAIN_FRAGMENT = "LoginCustomerMainFragment"
        val LOGIN_CUSTOMER_FIND_PW_FRAGMENT = "LoginCustomerFindPwFragment"
        val LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT ="LoginCustomerToEmailFragment"
        val MY_PAGE_CUSTOMER_AUCTION_FRAGMENT = "MyPageCustomerAuctionFragment"
        val MY_PAGE_CUSTOMER_MODIFY_FRAGMENT = "MyPageCustomerModifyFragment"
        val MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT ="MyPageCustomerPurchaseFragment"
        val MY_PAGE_CUSTOMER_QNA_FRAGMENT = "MyPageCustomerQnAFragment"
        val MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT = "MyPageCustomerQnADetailFragment"
        val MY_PAGE_CUSTOMER_REVIEW_FRAGMENT = "MyPageCustomerReviewFragment"
        val MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT = "MyPageCustomerReviewFragment"
        val MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAMGNET = "MyPageCustomerReviewDetailFragment"
        val MY_PAGE_CUSTOMER_WISH_FRAGMENT = "MyPageCustomerWishFragment"
        val ORDER_CHECK_FORM_CUSTOMER_FRAGMENT = "OrderCheckFromCustomerFragment"
        val ORDER_FORM_CUSTOMER_FRAGMENT = "OrderFormCustomerFragment"
        val PRODUCT_CUSTOMER_DETAIL_FRAGMENT ="ProductCustomerDetailFragment"
        val STORE_INFO_CUSTOMER_FRAGMENT = "StoreInfoCustomerFragment"
        val STORE_INFO_CUSTOMER_DETAIL_FRAGMENT = "StoreInfoCustomerDetailFragment"
        val SEARCH_ADDRESS_FRAGMENT = "SearchAddressFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()


        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:String, addToBackStack:Boolean, bundle:Bundle?){

        SystemClock.sleep(200)

        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // newFragment 에 Fragment가 들어있으면 oldFragment에 넣어준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        // 새로운 Fragment를 담을 변수
        newFragment = when(name){
            ALERT_CUSTOMER_FRAGMENT -> AlertCustomerFragment()
            AUCTION_CUSTOMER_FRAGMENT -> AuctionCustomerFragment()
            AUCTION_CUSTOMER_DETAIL_FRAMGNET -> AuctionCustomerDetailFragment()
            CART_CUSTOMER_FRAGMENT -> CartCustomerFragment()
            HOME_CUSTOMER_MAIN_FRAMGNET -> HomeCustomerMainFragment()
            HOME_CUSTOMER_SEARCH_FRAGMENT-> HomeCustomerSearchFragment()
            JOIN_CUSTOMER_MAIN_FRAGMENT -> JoinCustomerMainFragment()
            LOGIN_CUSTOMER_FIND_PW_FRAGMENT -> LoginCustomerFindPwFragment()

            LOGIN_CUSTOMER_MAIN_FRAGMENT -> LoginCustomerMainFragment()
            LOGIN_CUSTOMER_TO_EMAIL_FRAGMENT -> LoginCustomerToEmailFragment()
            MY_PAGE_CUSTOMER_AUCTION_FRAGMENT->MyPageCustomerAuctionFragment()
            MY_PAGE_CUSTOMER_MODIFY_FRAGMENT -> MyPageCustomerModifyFragment()
            MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT -> MyPageCustomerPurchaseFragment()
            MY_PAGE_CUSTOMER_QNA_FRAGMENT -> MyPageCustomerQnAFragment()
            MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT -> MyPageCustomerQnADetailFragment()
            MY_PAGE_CUSTOMER_REVIEW_FRAGMENT -> MyPageCustomerReviewFragment()
            MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT ->MyPageCustomerReviewWrite()
            MY_PAGE_CUSTOEMR_REVIEW_DETAIL_FRAMGNET-> MyPageCustomerReviewDetailFragment()
            MY_PAGE_CUSTOMER_WISH_FRAGMENT -> MyPageCustomerWishFragment()
            ORDER_CHECK_FORM_CUSTOMER_FRAGMENT -> OrderCheckFormCustomerFragment()
            ORDER_FORM_CUSTOMER_FRAGMENT -> OrderFormCustomerFragment()
            PRODUCT_CUSTOMER_DETAIL_FRAGMENT-> ProductCustomerDetailFragment()
            STORE_INFO_CUSTOMER_FRAGMENT -> StoreInfoCustomerFragment()
            STORE_INFO_CUSTOMER_DETAIL_FRAGMENT -> StoreInfoCustomerDetailFragment()
            SEARCH_ADDRESS_FRAGMENT -> SearchAddressFragment()

            else -> Fragment()
        }

        newFragment?.arguments = bundle

        if(newFragment != null) {

            // 애니메이션 설정
            if(oldFragment != null){
                oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
                oldFragment?.enterTransition = null
                oldFragment?.returnTransition = null
            }

            newFragment?.exitTransition = null
            newFragment?.reenterTransition = null
            newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.container_main, newFragment!!)

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 입력 요소에 포커스를 주는 메서드
    fun showSoftInput(view: View){
        view.requestFocus()

        val inputMethodManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        thread {
            SystemClock.sleep(200)
            inputMethodManger.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }



}