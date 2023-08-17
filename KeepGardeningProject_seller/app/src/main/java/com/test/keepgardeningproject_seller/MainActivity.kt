package com.test.keepgardeningproject_seller

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.test.keepgardeningproject_seller.UI.AlterSeller.AlterSellerFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerDetail.AuctionSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerEdit.AuctionSellerEditFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerInfo.AuctionSellerInfoFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerQnA.AuctionSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.AuctionSellerRegister.AuctionSellerRegisterFragment
import com.test.keepgardeningproject_seller.UI.HomeSeller.HomeSellerFragment
import com.test.keepgardeningproject_seller.UI.JoinSellerMain.JoinSellerMainFragment
import com.test.keepgardeningproject_seller.UI.LoginSellerMain.LoginSellerMainFragment
import com.test.keepgardeningproject_seller.UI.LoginSellerFindPw.LoginSellerFindPwFragment
import com.test.keepgardeningproject_seller.UI.LoginSellerToEmail.LoginSellerToEmailFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerAuction.MyPageSellerAuctionFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerModify.MyPageSellerModifyFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerProductState.MyPageSellerProductStateFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerPurchase.MyPageSellerPurchaseFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerQnA.MyPageSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerQnADetail.MyPageSellerQnADetailFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerReview.MyPageSellerReviewFragment
import com.test.keepgardeningproject_seller.UI.MyPageSellerReviewDetail.MyPageSellerReviewDetailFragment
import com.test.keepgardeningproject_seller.UI.OrderCheckFormSeller.OrderCheckFormSellerFragment
import com.test.keepgardeningproject_seller.UI.OrderFormSeller.OrderFormSellerFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerDetail.ProductSellerDetailFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerEdit.ProductSellerEditFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerRegister.ProductSellerRegisterFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerReview.ProductSellerReviewFragment
import com.test.keepgardeningproject_seller.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    var newFragment:Fragment? = null
    var oldFragment:Fragment? = null

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.INTERNET
    )
    companion object{
        val ALTER_SELLER_FRAGMENT = "AlterSellerFragment"
        val AUCTION_SELLER_DETAIL_FRAGMENT = "AuctionSellerDetailFragment"
        val AUCTION_SELLER_EDIT_FRAGMENT = "AuctionSellerEditFragment"
        val AUCTION_SELLER_INFO_FRAGMENT = "AuctionSellerInfoFragment"
        val AUCTION_SELLER_MAIN_FRAGMENT = "AuctionSellerMainFragment"
        val AUCTION_SELLER_QNA_FRAGMENT = "AuctionSellerQnAFragment"
        val AUCTION_SELLER_REGISTER_FRAGMENT = "AuctionSellerRegisterFragment"
        val HOME_SELLER_FRAGMENT = "HomeSellerFragment"
        val JOIN_SELLER_MAIN_FRAGMENT = "JoinSellerMainFragment"
        val LOGIN_SELLER_MAIN_FRAGMENT = "LoginSellerMainFragment"
        val LOGIN_SELLER_FIND_PW_FRAGMENT = "LoginSellerFindPwFragment"
        val LOGIN_SELLER_TO_EMAIL_FRAGMENT = "LoginSellerToEmailFragment"
        val MY_PAGE_SELLER_AUCTION_FRAGMENT = "MyPageSellerAuctionFragment"
        val MY_PAGE_SELLER_MODIFY_FRAGMENT = "MyPageSellerModifyFragment"
        val MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT = "MyPageSellerProductStateFragment"
        val MY_PAGE_SELLER_PURCHASE_FRAGMENT = "MyPageSellerPurchaseFragment"
        val MY_PAGE_SELLER_QNA_FRAGMENT = "MyPageSellerQnAFragment"
        val MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT = "MyPageSellerQnADetailFragment"
        val MY_PAGE_SELLER_REVIEW_FRAGMNET = "MyPageSellerReviewFragment"
        val MY_PAGE_SELLER_REVIEW_DETAIL_FRAGMENT = "MyPageSellerReviewDetailFragment"
        val ORDER_CHECK_FORM_SELLER_FRAGMENT = "OrderCheckFormSellerFragment"
        val ORDER_FORM_SELLER_FRAGMENT = "OrderFormSellerFragment"
        val PRODUCT_SELLER_DETAIL_FRAMGNET = "ProductSellerDetailFragment"
        val PRODUCT_SELLER_EDIT_FRAMGNET = "ProductSellerEditFragment"
        val PRODUCT_SELLER_MAIN_FRAMGNET = "ProductSellerMainFragment"
        val PRODUCT_SELLER_QNA_FRAMGNET = "ProductSellerQnAFragment"
        val PRODUCT_SELLER_REGISTER_FRAMGNET = "ProductSellerRegisterFragment"
        val PRODUCT_SELLER_REVIEW_FRAMGNET = "ProductSellerReviewFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //commit test

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
            ALTER_SELLER_FRAGMENT-> AlterSellerFragment()
            AUCTION_SELLER_DETAIL_FRAGMENT -> AuctionSellerDetailFragment()
            AUCTION_SELLER_EDIT_FRAGMENT-> AuctionSellerEditFragment()
            AUCTION_SELLER_INFO_FRAGMENT-> AuctionSellerInfoFragment()
            AUCTION_SELLER_MAIN_FRAGMENT-> AuctionSellerMainFragment()
            AUCTION_SELLER_QNA_FRAGMENT-> AuctionSellerQnAFragment()
            AUCTION_SELLER_REGISTER_FRAGMENT-> AuctionSellerRegisterFragment()
            HOME_SELLER_FRAGMENT-> HomeSellerFragment()
            JOIN_SELLER_MAIN_FRAGMENT-> JoinSellerMainFragment()
            LOGIN_SELLER_MAIN_FRAGMENT-> LoginSellerMainFragment()
            LOGIN_SELLER_FIND_PW_FRAGMENT-> LoginSellerFindPwFragment()
            LOGIN_SELLER_TO_EMAIL_FRAGMENT->LoginSellerToEmailFragment()
            MY_PAGE_SELLER_AUCTION_FRAGMENT -> MyPageSellerAuctionFragment()
            MY_PAGE_SELLER_MODIFY_FRAGMENT->MyPageSellerModifyFragment()
            MY_PAGE_SELLER_PURCHASE_FRAGMENT-> MyPageSellerPurchaseFragment()
            MY_PAGE_SELLER_QNA_FRAGMENT-> MyPageSellerQnAFragment()
            MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT -> MyPageSellerQnADetailFragment()
            MY_PAGE_SELLER_REVIEW_FRAGMNET-> MyPageSellerReviewFragment()
            MY_PAGE_SELLER_REVIEW_DETAIL_FRAGMENT->MyPageSellerReviewDetailFragment()
            MY_PAGE_SELLER_PRODUCT_STATE_FRAGMENT-> MyPageSellerProductStateFragment()
            ORDER_FORM_SELLER_FRAGMENT -> OrderFormSellerFragment()
            ORDER_CHECK_FORM_SELLER_FRAGMENT->OrderCheckFormSellerFragment()
            PRODUCT_SELLER_MAIN_FRAMGNET-> ProductSellerMainFragment()
            PRODUCT_SELLER_DETAIL_FRAMGNET->ProductSellerDetailFragment()
            PRODUCT_SELLER_EDIT_FRAMGNET->ProductSellerEditFragment()
            PRODUCT_SELLER_QNA_FRAMGNET->ProductSellerQnAFragment()
            PRODUCT_SELLER_REGISTER_FRAMGNET->ProductSellerRegisterFragment()
            PRODUCT_SELLER_REVIEW_FRAMGNET -> ProductSellerReviewFragment()


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