package com.test.keepgardeningproject_seller.UI.MyPageSellerQnA

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.Repository.QnARepository
import com.test.keepgardeningproject_seller.databinding.FragmentMyPageSellerQnABinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerQuaBinding
import com.test.keepgardeningproject_seller.databinding.RowMyPageSellerReviewBinding
import java.net.URL

class MyPageSellerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageSellerQnAFragment()
    }

    private lateinit var viewModel: MyPageSellerQnAViewModel

    lateinit var binding: FragmentMyPageSellerQnABinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageSellerQnABinding.inflate(inflater)

        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[MyPageSellerQnAViewModel::class.java]

        var useridx = mainActivity.loginSellerInfo.userSellerIdx

        viewModel.run {
            qnalist.observe(mainActivity){
                binding.recyclerViewSellerQuestion.adapter?.notifyDataSetChanged()
            }
        }
        binding.run{

            viewModel.getData(useridx)
            materialToolbarSellerQuestion.run{

                title = "문의내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {
                    viewModel.resetList()
                    mainActivity.removeFragment(MainActivity.MY_PAGE_SELLER_QNA_FRAGMENT)

                }

            }

            recyclerViewSellerQuestion.run{

                adapter = QnaRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))

            }

        }

        return binding.root
    }

    inner class QnaRecyclerViewAdapter : RecyclerView.Adapter<QnaRecyclerViewAdapter.AllViewHolder>(){
        inner class AllViewHolder(rowPostListBinding: RowMyPageSellerQuaBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){

            val rowPostListState:TextView
            val rowPostListStoreName: TextView
            val rowPostImg :ImageView
            var rowPostProductName:TextView
            val rowPostComment:TextView
            val rowArrow:ImageButton

            init{
                rowPostListState = rowPostListBinding.textviewQsReplyState
                rowPostListStoreName = rowPostListBinding.textviewQsStoreName
                rowPostProductName = rowPostListBinding.textviewQsProductName
                rowPostComment = rowPostListBinding.textviewQsComment
                rowPostImg = rowPostListBinding.imageviewQsImg
                rowArrow  = rowPostListBinding.buttonQsDetail



                rowArrow.setOnClickListener {
                    val qnaidx = viewModel.qnalist.value?.get(adapterPosition)?.qnaidx!!.toInt()
                    val newBundle = Bundle()
                    newBundle.putInt("qnaIdx", qnaidx!!)
                    mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT, true, newBundle)

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):QnaRecyclerViewAdapter.AllViewHolder {
            val rowPostListBinding = RowMyPageSellerQuaBinding.inflate(layoutInflater)
            val allViewHolder = AllViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
            holder.rowPostListState.text = viewModel.qnalist.value!!.get(position).qnaAnswer
            holder.rowPostListStoreName.text = viewModel.qnalist.value!!.get(position).userName

            holder.rowPostProductName.text = viewModel.qnalist.value!!.get(position).productname
            holder.rowPostComment.text = viewModel.qnalist.value!!.get(position).qnaTitle
           QnARepository.getQnaImage(viewModel.qnalist.value?.get(position)?.qnaimg.toString()){

                //이미지뷰에 사진넣기
                val url = URL(it.result.toString())
                Glide.with(context!!).load(url)
                    .override(200,200)
                    .into(holder.rowPostImg)
            }

        }

        override fun getItemCount(): Int {
            return viewModel.qnalist.value?.size!!
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetList()
    }

}