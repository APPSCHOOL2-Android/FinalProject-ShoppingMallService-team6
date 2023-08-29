package com.test.keepgardeningproject_customer.UI.StoreInfoCustomer

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.CartRepository
import com.test.keepgardeningproject_customer.Repository.StoreRepository
import com.test.keepgardeningproject_customer.databinding.FragmentStoreInfoCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowCartCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowStoreInfoCustomerBinding
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class StoreInfoCustomerFragment : Fragment() {
    lateinit var fragmentStoreInfoCustomerBinding: FragmentStoreInfoCustomerBinding
    lateinit var mainActivity: MainActivity

    private lateinit var storeInfoCustomerViewModel: StoreInfoCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStoreInfoCustomerBinding = FragmentStoreInfoCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        storeInfoCustomerViewModel = ViewModelProvider(mainActivity)[StoreInfoCustomerViewModel::class.java]
        storeInfoCustomerViewModel.run {
            storeInfoStoreList.observe(mainActivity) {
                fragmentStoreInfoCustomerBinding.recyclerViewStoreInfo.adapter?.notifyDataSetChanged()
            }

            storeInfoImageList.observe(mainActivity) {
                fragmentStoreInfoCustomerBinding.recyclerViewStoreInfo.adapter?.notifyDataSetChanged()
            }
        }

        fragmentStoreInfoCustomerBinding.run {
            toolbarStoreInfo.run {
                title = "스토어"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.STORE_INFO_CUSTOMER_FRAGMENT)
                }
            }

            recyclerViewStoreInfo.run {
                adapter = StoreInfoRecyclerViewAdpater()
                layoutManager = LinearLayoutManager(context)
            }

            storeInfoCustomerViewModel.getAllStoreInfo()
        }

        return fragmentStoreInfoCustomerBinding.root
    }

    inner class StoreInfoRecyclerViewAdpater : RecyclerView.Adapter<StoreInfoRecyclerViewAdpater.StoreInfoViewHolder>() {
        inner class StoreInfoViewHolder(rowStoreInfoCustomerBinding: RowStoreInfoCustomerBinding) :
            RecyclerView.ViewHolder(rowStoreInfoCustomerBinding.root) {

            var rowStoreName: TextView
            var rowStoreImage: ImageView

            init {
                rowStoreName = rowStoreInfoCustomerBinding.textViewRowStoreInfoStoreName
                rowStoreImage = rowStoreInfoCustomerBinding.imageViewRowStoreInfo1

                rowStoreInfoCustomerBinding.root.setOnClickListener {
                    val storeIdx = storeInfoCustomerViewModel.storeInfoStoreList.value?.get(adapterPosition)?.userSellerIdx!!
                    val bundle = Bundle()
                    bundle.putLong("storeIdx", storeIdx)

                    mainActivity.replaceFragment(MainActivity.STORE_INFO_CUSTOMER_DETAIL_FRAGMENT, true, bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoViewHolder {
            val rowStoreInfoCustomerBinding = RowStoreInfoCustomerBinding.inflate(layoutInflater)
            val storeInfoViewHolder = StoreInfoViewHolder(rowStoreInfoCustomerBinding)

            rowStoreInfoCustomerBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return storeInfoViewHolder
        }

        override fun getItemCount(): Int {
            return storeInfoCustomerViewModel.storeInfoStoreList.value?.size!!
        }

        override fun onBindViewHolder(holder: StoreInfoViewHolder, position: Int) {
            var fileName = storeInfoCustomerViewModel.storeInfoImageList.value?.get(position)!!
            Log.i("f222", fileName)
            if (fileName != "None") {
                StoreRepository.getImage(fileName) {
                    var fileUri = it.result
                    Glide.with(mainActivity).load(fileUri).into(holder.rowStoreImage)
//                    thread {
//                        // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
//                        val url = URL(it.result.toString())
//                        // 접속한다.
//                        val httpURLConnection = url.openConnection() as HttpURLConnection
//                        // 이미지 객체를 생성한다.
//                        val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)
//                        holder.rowStoreImage.setImageBitmap(bitmap)
//                    }
                }
            }

            holder.rowStoreName.text = storeInfoCustomerViewModel.storeInfoStoreList.value?.get(position)?.userSellerStoreName
        }
    }
}