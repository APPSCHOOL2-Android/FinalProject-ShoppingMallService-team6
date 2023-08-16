package com.test.keepgardeningproject_customer.UI.HomeCustomerMain

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome.HomeCustomerMainHomeFragment
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMainBinding
import com.test.keepgardeningproject_customer.databinding.HeaderHomeCustomerMainBinding

class HomeCustomerMainFragment : Fragment() {

    lateinit var fragmentHomeCustomerMainBinding: FragmentHomeCustomerMainBinding
    lateinit var mainActivity: MainActivity

    // 프래그먼트 전환
    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null
    companion object{
        val HOME_CUSTOMER_MAIN_HOME = "HomeCustomerMainHomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeCustomerMainBinding = FragmentHomeCustomerMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentHomeCustomerMainBinding.run{
            // 시작시 홈화면
            replaceFragment(HOME_CUSTOMER_MAIN_HOME,false,null)

            // 툴바
            toolbarHcm.run{
                // 메뉴버튼
                imageHcmCategory.setOnClickListener{
                    drawerHcm.open()
                }

                // 장바구니
                imageHcmOrder.setOnClickListener {

                }

                // 알람
                imageHcmAlarm.setOnClickListener {

                }
            }

            //네비게이션 드로어
            navigationHcm.run{
                // 헤더 설정
                val headerHomeCustomerMainBinding = HeaderHomeCustomerMainBinding.inflate(inflater)
                headerHomeCustomerMainBinding.textViewHcmHeaderTitle.text = "홍길동님 환영합니다"
                addHeaderView(headerHomeCustomerMainBinding.root)

                // 드로어 닫기
                headerHomeCustomerMainBinding.imageHcmHeaderArrowBack.setOnClickListener {
                    drawerHcm.close()
                }

                // 드로어 열렸을때 네비게이션
                setNavigationItemSelectedListener {
                    when(it.itemId){
                        R.id.item_hcm_plant->{}
                        R.id.item_hcm_orchid->{}
                        R.id.item_hcm_monstera->{}
                        R.id.item_hcm_bonsai->{}
                        R.id.item_hcm_commodity->{}
                        R.id.item_hcm_seed->{}
                        R.id.item_hcm_bouquet->{}
                        R.id.item_hcm_pot->{}
                        R.id.item_hcm_store->{}
                        R.id.item_hcm_favorite->{}
                    }
                    false
                }
            }

            // 바텀네비게이션
            bottomHcm.run{
                setOnItemSelectedListener {
                    when(it.itemId){
                        R.id.item_hcm_category->{
                            drawerHcm.open()
                        }
                        R.id.item_hcm_home->{
                            replaceFragment(HOME_CUSTOMER_MAIN_HOME,false,null)
                        }

                        R.id.item_hcm_mypage->{
                            Toast.makeText(mainActivity, "마이페이지로 이동", Toast.LENGTH_SHORT).show()
                        }
                    }
                    true
                }

                selectedItemId = R.id.item_hcm_home
            }
        }

        return fragmentHomeCustomerMainBinding.root
    }

    fun replaceFragment(name:String, addToBackStack:Boolean, bundle:Bundle?){
        SystemClock.sleep(200)

        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()

        // newFragment 에 Fragment가 들어있으면 oldFragment에 넣어준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        newFragment = when(name){
            HOME_CUSTOMER_MAIN_HOME-> HomeCustomerMainHomeFragment()
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
            fragmentTransaction.replace(R.id.container_hcm, newFragment!!)

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    fun removeFragment(name:String){
        mainActivity.supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}