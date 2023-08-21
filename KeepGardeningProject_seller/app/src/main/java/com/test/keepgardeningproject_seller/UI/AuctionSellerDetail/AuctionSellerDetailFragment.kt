package com.test.keepgardeningproject_seller.UI.AuctionSellerDetail

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
import com.test.keepgardeningproject_seller.UI.AuctionSellerInfo.AuctionSellerInfoFragment
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerDetailBinding
import com.test.keepgardeningproject_seller.databinding.RowAuctionSellerDetailBinding
import com.test.keepgardeningproject_seller.databinding.RowAuctionSellerInfoBinding

class AuctionSellerDetailFragment : Fragment() {

    lateinit var fragmentAuctionSellerDetailBinding: FragmentAuctionSellerDetailBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerDetailFragment()
    }

    private lateinit var viewModel: AuctionSellerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerDetailBinding = FragmentAuctionSellerDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerDetailBinding.run {
            recyclerViewAuctionSellerDetail.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
            }

        }
        return fragmentAuctionSellerDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        fragmentAuctionSellerDetailBinding.root.requestLayout()

        var adapter = fragmentAuctionSellerDetailBinding.recyclerViewAuctionSellerDetail.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowAuctionSellerDetailBinding: RowAuctionSellerDetailBinding) : RecyclerView.ViewHolder(rowAuctionSellerDetailBinding.root) {

            var imageViewProductDetail : ImageView

            init {
                imageViewProductDetail = rowAuctionSellerDetailBinding.imageViewRowAuctionSellerDetail
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowAuctionSellerDetailBinding.inflate(layoutInflater)
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
            return 10
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.imageViewProductDetail.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

}