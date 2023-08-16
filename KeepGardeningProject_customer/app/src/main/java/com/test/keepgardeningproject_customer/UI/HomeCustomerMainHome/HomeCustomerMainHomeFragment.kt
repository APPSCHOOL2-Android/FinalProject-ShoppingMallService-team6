package com.test.keepgardeningproject_customer.UI.HomeCustomerMainHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerMainHomeBinding

class HomeCustomerMainHomeFragment : Fragment() {

    lateinit var fragmentHomeCustomerMainHomeBinding: FragmentHomeCustomerMainHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeCustomerMainHomeBinding = FragmentHomeCustomerMainHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentHomeCustomerMainHomeBinding.run{
            searchBarHcmh.run{
                hint = "검색어를 입력해주세요"

            }

            searchViewHcmh.run{
                hint = "검색어를 입력해주세요"
                
            }
        }

        return fragmentHomeCustomerMainHomeBinding.root
    }

}