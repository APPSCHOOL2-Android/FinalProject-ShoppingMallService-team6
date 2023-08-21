package com.test.keepgardeningproject_seller.UI.AuctionSellerRegister

import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerRegisterBinding

class AuctionSellerRegisterFragment : Fragment() {

    lateinit var fragmentAuctionSellerRegisterBinding: FragmentAuctionSellerRegisterBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerRegisterFragment()
    }

    private lateinit var viewModel: AuctionSellerRegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerRegisterBinding = FragmentAuctionSellerRegisterBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerRegisterBinding.run {

            toolbarAuctionSellerRegister.run {
                title = "경매 등록"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_SELLER_REGISTER_FRAGMENT)
                }
            }

            datePickerAuctionSellerRegisterEndDate.minDate = System.currentTimeMillis()


            buttonAuctionSellerRegisterRegister.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setIcon(R.drawable.ic_warning_24px)
                builder.setTitle("경고")
                builder.setMessage("경매가 시작되면 \n경매가격, 마감날짜를 \n수정하실 수 없습니다.")
                builder.setNegativeButton("취소",null)
                builder.setPositiveButton("등록") { dialogInterface: DialogInterface, i: Int ->
                    val newBundle = Bundle()
                    newBundle.putString("oldFragment", "AuctionSellerRegisterFragment")
                    mainActivity.replaceFragment(MainActivity.AUCTION_SELLER_MAIN_FRAGMENT,true,newBundle)
                }
                builder.show()
            }
        }
        return fragmentAuctionSellerRegisterBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}