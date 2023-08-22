package com.test.keepgardeningproject_seller.UI.JoinSellerAddInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.keepgardeningproject_seller.API.KakaoAPI
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentJoinSellerAddInfoBinding


class JoinSellerAddInfoFragment : Fragment() {
    lateinit var fragmentJoinSellerAddInfoBinding:FragmentJoinSellerAddInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentJoinSellerAddInfoBinding = FragmentJoinSellerAddInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        var kakaoApi = KakaoAPI()
        fragmentJoinSellerAddInfoBinding.run {
            val joinUserType = arguments?.getString("joinUserType")
            val joinUserEmail = arguments?.getString("joinUserEmail")
            textInputEditTextJoinSellerAddInfoEmail.setText(joinUserEmail)

            buttonJoinSellerAddInfoJoin.setOnClickListener {
                kakaoApi.LogOut()
            }
        }


        return fragmentJoinSellerAddInfoBinding.root
    }


}