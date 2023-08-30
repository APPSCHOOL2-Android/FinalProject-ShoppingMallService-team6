package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnA

import android.icu.lang.UCharacter.VerticalOrientation
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.DAO.MypageQnAData
import com.test.keepgardeningproject_customer.DAO.MypageQnADetailData

import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.PurchaseRepository
import com.test.keepgardeningproject_customer.UI.HomeCustomerMain.HomeCustomerMainFragment
import com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase.MyPageCustomerPurchaseFragment
import com.test.keepgardeningproject_customer.databinding.ActivityMainBinding
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerQnABinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseButtonBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerQnaBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerReviewBinding
import org.w3c.dom.Text
import java.net.URL

class MyPageCustomerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnAFragment()
    }

    private lateinit var viewmodel: MyPageCustomerQnAViewModel

    lateinit var binding: FragmentMyPageCustomerQnABinding

    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageCustomerQnABinding.inflate(inflater)
        mainActivity = activity as MainActivity
        viewmodel = ViewModelProvider(mainActivity).get(MyPageCustomerQnAViewModel::class.java)

        viewmodel.run {
            qnalist.observe(mainActivity){
                binding.MyPageCustomerQuestionRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
        binding.run{
            viewmodel.getData()
            materialToolbarQc.run{
                title = "문의 내역"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    viewmodel.resetList()
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_FRAGMENT)
                }
            }

            MyPageCustomerQuestionRecyclerView.run{
                adapter = QnaRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))

            }


        }

        return binding.root
    }

    inner class QnaRecyclerViewAdapter : RecyclerView.Adapter<QnaRecyclerViewAdapter.AllViewHolder>(){
        inner class AllViewHolder(rowPostListBinding: RowMyPageCustomerQnaBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListState:TextView
            val rowPostListStoreName: TextView
            val rowPostImg :ImageView
            var rowPostProductName:TextView
            val rowPostComment:TextView
            var rowArrow: TextView

            init{
                rowPostListState = rowPostListBinding.textviewQcReplyState
                rowPostListStoreName = rowPostListBinding.textviewQcStoreName
                rowPostProductName = rowPostListBinding.textviewQcProductName
                rowPostComment = rowPostListBinding.textviewQcComment
                rowPostImg = rowPostListBinding.imageviewQcImg
                rowArrow  = rowPostListBinding.textviewQcDetail


                rowArrow.setOnClickListener {
                    val qnaidx = viewmodel.qnalist.value?.get(adapterPosition)?.qnaIdx
                    val newBundle = Bundle()
                    newBundle.putLong("qnaIdx", qnaidx!!)
                    mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT, true, newBundle)

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):QnaRecyclerViewAdapter.AllViewHolder {
            val rowPostListBinding = RowMyPageCustomerQnaBinding.inflate(layoutInflater)
            val allViewHolder = AllViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
            holder.rowPostListState.text = viewmodel.qnalist.value!!.get(position).qnaAnswer
            holder.rowPostListStoreName.text = viewmodel.qnalist.value!!.get(position).qnaStoreName


            holder.rowPostProductName.text = viewmodel.qnalist.value!!.get(position).qnaName
            holder.rowPostComment.text = viewmodel.qnalist.value!!.get(position).qnaDetail
            PurchaseRepository.getImage(viewmodel.qnalist.value?.get(position)?.qnaImgList.toString()){

                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.rowPostImg)
            }

        }

        override fun getItemCount(): Int {
            return viewmodel.qnalist.value?.size!!
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewmodel.resetList()
    }

}