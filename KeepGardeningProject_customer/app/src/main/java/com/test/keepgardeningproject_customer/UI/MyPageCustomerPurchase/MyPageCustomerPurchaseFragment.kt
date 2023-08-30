package com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase


import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

import com.test.keepgardeningproject_customer.DAO.UserInfo
import com.test.keepgardeningproject_customer.DAO.purchaseInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.OrderProductRepository
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.Repository.UserRepository
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseButtonBinding
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MyPageCustomerPurchaseFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentMyPageCustomerPurchaseBinding: FragmentMyPageCustomerPurchaseBinding
    lateinit var purchaseviewModel: MyPageCustomerPurchaseViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMyPageCustomerPurchaseBinding = FragmentMyPageCustomerPurchaseBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        purchaseviewModel = ViewModelProvider(mainActivity)[MyPageCustomerPurchaseViewModel::class.java]
        purchaseviewModel.run {

            paymentList.observe(mainActivity){
                fragmentMyPageCustomerPurchaseBinding.recyclerviewPcp.adapter?.notifyDataSetChanged()
            }
            deliveryList.observe(mainActivity){
                fragmentMyPageCustomerPurchaseBinding.recyclerviewPcd.adapter?.notifyDataSetChanged()
            }

        }


        fragmentMyPageCustomerPurchaseBinding.run {
//            purchaseviewModel.getData()
//            purchaseviewModel.getData2()
            toolbarPc.run {
                setTitle("구매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    //마이페이지 메인화면으로 이동
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT)
                }
            }
            recyclerviewPcp.run {
                adapter =PaymentRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
            recyclerviewPcd.run {
                adapter = DeliveryRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
            purchaseviewModel.getPaymentAll()
            purchaseviewModel.getDeliveryAll()

        }


        return fragmentMyPageCustomerPurchaseBinding.root
    }


    //결제완료 리사이클러뷰
    inner class PaymentRecyclerViewAdapter : RecyclerView.Adapter<PaymentRecyclerViewAdapter.PaymentViewHolder>(){
        inner class PaymentViewHolder(rowPostListBinding: RowMyPageCustomerPurchaseBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListState:TextView
            val rowPostListNickName:TextView
            val imageviewPtimg :ImageView


            init{
                rowPostListState = rowPostListBinding.textviewPcState
                rowPostListNickName = rowPostListBinding.textViewPcProductName
                imageviewPtimg = rowPostListBinding.imageviewPcImg


                rowPostListBinding.root.setOnClickListener {

                    //구매자 인덱스
                    val purchaseInfoIdx = purchaseviewModel.paymentList.value?.get(adapterPosition)?.purchaseInfoIdx
                    val purchaseIdx = purchaseviewModel.paymentList.value?.get(adapterPosition)?.purchaseIdx
                    val totalorderIdx = purchaseviewModel.paymentList.value?.get(adapterPosition)?.totalorderIdx
                    val newBundle = Bundle()
                    newBundle.putLong("purchaseInfoIdx", purchaseInfoIdx!!)
                    newBundle.putLong("purchaseIdx", purchaseIdx!!)
                    newBundle.putLong("totalOrderIdx", totalorderIdx!!)
                    mainActivity.replaceFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
            val rowPostListBinding = RowMyPageCustomerPurchaseBinding.inflate(layoutInflater)
            val allViewHolder = PaymentViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return purchaseviewModel.paymentList.value?.size!!
        }

        override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
            holder.rowPostListNickName.text = purchaseviewModel.paymentList.value?.get(position)?.purchaseTitle
            holder.rowPostListState.text = " ["+purchaseviewModel.paymentList.value?.get(position)?.purchaseState+"] "
            PurchaseRepository.getImage(purchaseviewModel.paymentList.value?.get(position)?.purchaseImg.toString()){
                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.imageviewPtimg)
            }
        }

    }

    //배송완료 리사이클러뷰
    inner class DeliveryRecyclerViewAdapter : RecyclerView.Adapter<DeliveryRecyclerViewAdapter.DeliveryViewHolder>(){
        inner class DeliveryViewHolder(rowPostListBinding: RowMyPageCustomerPurchaseButtonBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListState:TextView
            val rowPostListNickName:TextView
            val imageviewPtimg :ImageView
            var rowButton:Button

            init{
                rowPostListState = rowPostListBinding.textviewPcState2
                rowPostListNickName = rowPostListBinding.textViewPcProductName2
                imageviewPtimg = rowPostListBinding.imageviewPcImg2
                rowButton  = rowPostListBinding.buttonPcReview2

                rowButton.setOnClickListener {
                    //구매자 인덱스

                    //2023.08.29일 오준용 수정함.
                    val ordersProductIdx = purchaseviewModel.deliveryList.value?.get(adapterPosition)?.orderproductIdx
                    val ordersIdx= purchaseviewModel.deliveryList.value?.get(adapterPosition)?.totalorderIdx
                    val newBundle = Bundle()
                    newBundle.putLong("ordersProductIdx", ordersProductIdx!!)
                    newBundle.putLong("ordersIdx", ordersIdx!!)

                    mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT, true, newBundle)

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
            val rowPostListBinding = RowMyPageCustomerPurchaseButtonBinding.inflate(layoutInflater)
            val allViewHolder = DeliveryViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
            holder.rowPostListNickName.text =   purchaseviewModel.deliveryList.value?.get(position)?.purchaseTitle
            holder.rowPostListState.text = " ["+purchaseviewModel.deliveryList.value?.get(position)?.purchaseState+"] "
            PurchaseRepository.getImage(purchaseviewModel.deliveryList.value?.get(position)?.purchaseImg.toString()){

                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.imageviewPtimg)
            }

        }

        override fun getItemCount(): Int {
            return purchaseviewModel.deliveryList.value?.size!!
        }



    }



}


