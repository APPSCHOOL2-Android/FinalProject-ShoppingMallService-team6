package com.test.keepgardeningproject_seller.UI.AuctionSellerQnA

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.UI.ProductSellerQnA.ProductSellerQnAFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerQnABinding
import com.test.keepgardeningproject_seller.databinding.RowProductSellerQnaBinding

class AuctionSellerQnAFragment : Fragment() {

    lateinit var fragmentAuctionSellerQnABinding: FragmentAuctionSellerQnABinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerQnAFragment()
    }

    private lateinit var viewModel: AuctionSellerQnAViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerQnABinding = FragmentAuctionSellerQnABinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerQnABinding.run {
            recyclerViewAuctionSellerQnA.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
        return fragmentAuctionSellerQnABinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerQnAViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        var adapter = fragmentAuctionSellerQnABinding.recyclerViewAuctionSellerQnA.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()

        fragmentAuctionSellerQnABinding.root.requestLayout()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowProductSellerQnaBinding: RowProductSellerQnaBinding) : RecyclerView.ViewHolder(rowProductSellerQnaBinding.root) {

            var imageViewRow : ImageView
            var textViewRowTitle : TextView
            var textViewRowContent : TextView

            init {
                imageViewRow = rowProductSellerQnaBinding.imageViewProductSellerQnA
                textViewRowTitle = rowProductSellerQnaBinding.textViewProductSellerQnAReviewTitle
                textViewRowContent = rowProductSellerQnaBinding.textViewProductSellerQnAReviewContent
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
            return 3
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.imageViewRow.setImageResource(R.drawable.img_bonsai)
            holder.textViewRowTitle.text = "문의 제목"
            holder.textViewRowContent.text = "문의 내용"
        }
    }

}