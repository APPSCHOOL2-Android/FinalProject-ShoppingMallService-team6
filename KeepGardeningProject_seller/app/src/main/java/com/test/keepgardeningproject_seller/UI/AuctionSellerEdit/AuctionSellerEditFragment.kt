package com.test.keepgardeningproject_seller.UI.AuctionSellerEdit

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.DAO.AuctionProductClass
import com.test.keepgardeningproject_seller.DAO.ProductClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.AUCTION_SELLER_EDIT_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainFragment.Companion.auctionProductIdx
import com.test.keepgardeningproject_seller.UI.AuctionSellerMain.AuctionSellerMainViewModel
import com.test.keepgardeningproject_seller.UI.ProductSellerEdit.ProductSellerEditFragment
import com.test.keepgardeningproject_seller.UI.ProductSellerEdit.ProductSellerEditViewModel
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerEditBinding
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerEditBinding
import com.test.keepgardeningproject_seller.databinding.RowSellerRegisterBinding

class AuctionSellerEditFragment : Fragment() {

    lateinit var fragmentAuctionSellerEditBinding: FragmentAuctionSellerEditBinding
    lateinit var mainActivity: MainActivity

    lateinit var auctionSellerEditViewModel: AuctionSellerEditViewModel

    var uriList = mutableListOf<Uri>()
    var fileNameList = mutableListOf<String>()
    var imageList = ArrayList<String>()

    val MAX_IMAGE_NUM = 3

    companion object {
        var originImageNum = 0
        fun newInstance() = AuctionSellerEditFragment()
    }

    private lateinit var viewModel: AuctionSellerEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerEditBinding = FragmentAuctionSellerEditBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        auctionSellerEditViewModel = ViewModelProvider(mainActivity)[AuctionSellerEditViewModel::class.java]
        auctionSellerEditViewModel.run {

            auctionProductName.observe(mainActivity) {
                fragmentAuctionSellerEditBinding.textInputEditTextAuctionSellerEditProductName.setText(it)
            }
            auctionProductDetail.observe(mainActivity) {
                fragmentAuctionSellerEditBinding.textInputEditTextAuctionSellerEditProductDetail.setText(it)
            }
            auctionProductImageNameList.observe(mainActivity) {
                fileNameList = it
                fragmentAuctionSellerEditBinding.recyclerViewAuctionSellerEditImage.adapter?.notifyDataSetChanged()
            }
            auctionProductImageUriList.observe(mainActivity) {
                uriList = it
                fragmentAuctionSellerEditBinding.recyclerViewAuctionSellerEditImage.adapter?.notifyDataSetChanged()
            }
            auctionSellerEditViewModel.getAuctionProductInfo(auctionProductIdx.toLong())
        }

        fragmentAuctionSellerEditBinding.run {

            toolbarAuctionSellerEdit.run {
                title = "경매 수정"

                setNavigationIcon(R.drawable.ic_back_24px)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.DKGRAY, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_SELLER_EDIT_FRAGMENT)
                }
            }

            recyclerViewAuctionSellerEditImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)

                adapter?.notifyDataSetChanged()
            }

            buttonAuctionSellerEditEdit.setOnClickListener {

                var auctionProductName = textInputEditTextAuctionSellerEditProductName.text.toString()
                var auctionProductContent = textInputEditTextAuctionSellerEditProductDetail.text.toString()

                if(auctionProductName.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 이름을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAuctionSellerEditProductName)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(auctionProductContent.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상세 내용을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAuctionSellerEditProductDetail)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                mainActivity.removeFragment(AUCTION_SELLER_EDIT_FRAGMENT)
            }
        }
        return fragmentAuctionSellerEditBinding.root
    }

    override fun onResume() {
        super.onResume()
        var adapter = fragmentAuctionSellerEditBinding.recyclerViewAuctionSellerEditImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerEditViewModel::class.java)
        // TODO: Use the ViewModel
    }


    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowSellerRegisterBinding: RowSellerRegisterBinding) : RecyclerView.ViewHolder(rowSellerRegisterBinding.root) {

            var imageViewProduct : ImageView

            init {
                imageViewProduct = rowSellerRegisterBinding.imageViewRowSellerRegister

                // context 메뉴 구성 (context 메뉴 활성화)
                rowSellerRegisterBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->

                    mainActivity.menuInflater.inflate(R.menu.delete_menu, menu)

                    // context menu의 항목 선택시 실행되는 함수
                    menu[0].setOnMenuItemClickListener {

                        uriList.removeAt(adapterPosition)
                        fileNameList.removeAt(adapterPosition)
                        originImageNum--

                        // recyclerView 갱신
                        this@RecyclerAdapterClass.notifyDataSetChanged()

                        false
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowSellerRegisterBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            return viewHolder
        }

        override fun getItemCount(): Int {
            return uriList.count()
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            Glide.with(mainActivity).load(uriList[position]).into(holder.imageViewProduct)
        }
    }
}