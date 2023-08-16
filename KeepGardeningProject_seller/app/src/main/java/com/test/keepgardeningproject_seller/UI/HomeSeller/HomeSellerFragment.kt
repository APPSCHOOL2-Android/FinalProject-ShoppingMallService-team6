package com.test.keepgardeningproject_seller.UI.HomeSeller

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_REGISTER_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerBinding

class HomeSellerFragment : Fragment() {

    lateinit var fragmentHomeSellerBinding: FragmentHomeSellerBinding
    lateinit var mainActivity: MainActivity


    companion object {
        fun newInstance() = HomeSellerFragment()
    }

    private lateinit var viewModel: HomeSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeSellerBinding = FragmentHomeSellerBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentHomeSellerBinding.run {
            bottomNavigationViewHomeSeller.run {
                selectedItemId = R.id.item_bottomMenu_home
                setOnItemSelectedListener {
                    when(it.itemId) {
                        R.id.item_bottomMenu_home -> {
                        }
                        R.id.item_bottomMenu_registerProduct -> {
                        }
                        R.id.item_bottomMenu_mypage -> {
                        }
                    }
                    true
                }
            }
        }

        return fragmentHomeSellerBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeSellerViewModel::class.java)
        // TODO: Use the ViewModel
    }
}