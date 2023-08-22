package com.test.keepgardeningproject_seller.UI.AuctionSellerInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentAuctionSellerInfoBinding
import com.test.keepgardeningproject_seller.databinding.RowAuctionSellerInfoBinding
import com.test.keepgardeningproject_seller.databinding.RowProductSellerQnaBinding

class AuctionSellerInfoFragment : Fragment() {

    lateinit var fragmentAuctionSellerInfoBinding: FragmentAuctionSellerInfoBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = AuctionSellerInfoFragment()
    }

    private lateinit var viewModel: AuctionSellerInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAuctionSellerInfoBinding = FragmentAuctionSellerInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAuctionSellerInfoBinding.run {
            recyclerViewAuctionSellerInfo.run {

                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }
        return fragmentAuctionSellerInfoBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuctionSellerInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        var adapter = fragmentAuctionSellerInfoBinding.recyclerViewAuctionSellerInfo.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()

        fragmentAuctionSellerInfoBinding.root.requestLayout()
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowAuctionSellerInfoBinding: RowAuctionSellerInfoBinding) : RecyclerView.ViewHolder(rowAuctionSellerInfoBinding.root) {

            var textViewRowIndex : TextView
            var textViewRowNickname : TextView
            var textViewRowTime : TextView
            var textViewRowPrice : TextView

            init {
                textViewRowIndex = rowAuctionSellerInfoBinding.textViewRowAuctionSellerInfoUserIndex
                textViewRowNickname = rowAuctionSellerInfoBinding.textViewRowAuctionSellerInfoUserNickName
                textViewRowTime = rowAuctionSellerInfoBinding.textViewRowAuctionSellerInfoTime
                textViewRowPrice = rowAuctionSellerInfoBinding.textViewRowAuctionSellerInfoPrice
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowAuctionSellerInfoBinding.inflate(layoutInflater)
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
        }
    }

}