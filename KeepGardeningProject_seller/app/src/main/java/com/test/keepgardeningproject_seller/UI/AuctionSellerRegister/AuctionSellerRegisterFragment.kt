package com.test.keepgardeningproject_seller.UI.AuctionSellerRegister

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.AuctionProductRepository
import com.test.keepgardeningproject_seller.Repository.ProductRepository
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerRegisterBinding
import com.test.keepgardeningproject_seller.databinding.RowSellerRegisterBinding
import java.util.Calendar

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

            recyclerViewAuctionSellerRegisterImage.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
            datePickerAuctionSellerRegisterEndDate.minDate = System.currentTimeMillis()


            buttonAuctionSellerRegisterRegister.setOnClickListener {

                var auctionProductName = textInputEditTextAuctionSellerRegisterProductName.text.toString()
                var auctionOpenPrice = textInputEditTextAuctionSellerRegisterOpenPrice.text.toString()
                var auctionProductContent = textInputEditTextAuctionSellerRegisterProductDetail.text.toString()

                // 입력받은 경매 종료 날짜 정보
                var year = datePickerAuctionSellerRegisterEndDate.year
                var month = datePickerAuctionSellerRegisterEndDate.month + 1
                var day = datePickerAuctionSellerRegisterEndDate.dayOfMonth

                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                val currentMonth = calendar.get(Calendar.MONTH) + 1
                val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)



                // 입력받은 경매 종료 시간 정보
                var hour = timePickerAuctionSellerRegisterEndDate.hour
                var minute = timePickerAuctionSellerRegisterEndDate.minute

                val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendar.get(Calendar.MINUTE)

                if(auctionProductName.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 이름을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAuctionSellerRegisterProductName)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(auctionOpenPrice.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상품 가격을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAuctionSellerRegisterOpenPrice)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(auctionProductContent.isEmpty()) {
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setMessage("상세 내용을 입력해주세요.")
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAuctionSellerRegisterProductDetail)
                    }
                    builder.show()

                    return@setOnClickListener
                }

                if(year == currentYear && month == currentMonth && day == currentDayOfMonth) {
                    if(hour < currentHour) {
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setMessage("마감 날짜는 현재 이전 시간으로\n설정할 수 없습니다.")
                        builder.setPositiveButton("확인",null)
                        builder.show()

                        return@setOnClickListener
                    }
                    else if(hour == currentHour && minute < currentMinute) {
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setMessage("마감 날짜는 현재 이전 시간으로\n설정할 수 없습니다.")
                        builder.setPositiveButton("확인",null)
                        builder.show()

                        return@setOnClickListener
                    }
                    else if(hour == currentHour && minute == currentMinute) {
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setMessage("마감 날짜는 현재와 동일한 시간으로\n설정할 수 없습니다.")
                        builder.setPositiveButton("확인",null)
                        builder.show()

                        return@setOnClickListener
                    }
                }



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

    override fun onResume() {
        super.onResume()
        var adapter = fragmentAuctionSellerRegisterBinding.recyclerViewAuctionSellerRegisterImage.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerRegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowSellerRegisterBinding: RowSellerRegisterBinding) : RecyclerView.ViewHolder(rowSellerRegisterBinding.root) {

            var imageViewProduct : ImageView

            init {
                imageViewProduct = rowSellerRegisterBinding.imageViewRowSellerRegister
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
            holder.imageViewProduct.setImageURI(uriList[position])
        }
    }

}