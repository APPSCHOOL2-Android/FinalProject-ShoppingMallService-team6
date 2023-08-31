package com.test.keepgardeningproject_seller.UI.ProductSellerQnA

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.DAO.QnAClass
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.UI.ProductSellerMain.ProductSellerMainFragment.Companion.productIdx
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerQnABinding
import com.test.keepgardeningproject_seller.databinding.RowProductSellerQnaBinding

class ProductSellerQnAFragment : Fragment() {

    lateinit var fragmentProductSellerQnABinding: FragmentProductSellerQnABinding
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: ProductSellerQnAViewModel

    companion object {
        fun newInstance() = ProductSellerQnAFragment()
    }

    var qnaList = mutableListOf<QnAClass>()
    var qnaAuctionList = mutableListOf<QnAClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerQnABinding = FragmentProductSellerQnABinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[ProductSellerQnAViewModel::class.java]
        viewModel.run {
            qnaClassList.observe(mainActivity) {
                qnaList = it
                for (i in 0 until qnaList.size) {
                    if(it.get(i).QnAProductType == "상품") {
                        qnaAuctionList.add(qnaList[i])
                    }
                }
                fragmentProductSellerQnABinding.recyclerViewProductSellerQnA.adapter?.notifyDataSetChanged()
            }
        }
        viewModel.getQnAInfoAll(productIdx.toLong())

        fragmentProductSellerQnABinding.run {
            recyclerViewProductSellerQnA.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
                adapter?.notifyDataSetChanged()
            }
        }

        return fragmentProductSellerQnABinding.root
    }

    override fun onResume() {
        super.onResume()

        qnaList.clear()

        var adapter = fragmentProductSellerQnABinding.recyclerViewProductSellerQnA.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()

        viewModel.getQnAInfoAll(productIdx.toLong())

        fragmentProductSellerQnABinding.root.requestLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerQnAViewModel::class.java)
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowProductSellerQnaBinding: RowProductSellerQnaBinding) : RecyclerView.ViewHolder(rowProductSellerQnaBinding.root) {

            var textViewRowTitle : TextView
            var textViewRowContent : TextView
            var textViewRowDate : TextView

            init {
                textViewRowTitle = rowProductSellerQnaBinding.textViewProductSellerQnAQnaTitle
                textViewRowContent = rowProductSellerQnaBinding.textViewProductSellerQnAQnaContent
                textViewRowDate = rowProductSellerQnaBinding.textViewProductSellerQnAQnaDate

                rowProductSellerQnaBinding.root.setOnClickListener {
                    val newBundle = Bundle()
                    newBundle.putInt("qnaIdx", qnaAuctionList[adapterPosition].QnAIdx!!.toInt())
                    mainActivity.replaceFragment(MainActivity.MY_PAGE_SELLER_QNA_DETAIL_FRAGMENT, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowProductSellerQnaBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return qnaAuctionList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowTitle.text = qnaAuctionList.get(position)?.QnATitle
            holder.textViewRowContent.text = qnaAuctionList.get(position)?.QnAContent
            holder.textViewRowDate.text = qnaAuctionList.get(position)?.QnADate
        }
    }

}