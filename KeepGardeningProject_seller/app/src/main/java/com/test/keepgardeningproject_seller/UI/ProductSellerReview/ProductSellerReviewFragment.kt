package com.test.keepgardeningproject_seller.UI.ProductSellerReview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.databinding.FragmentProductSellerReviewBinding
import com.test.keepgardeningproject_seller.databinding.RowProductSellerReviewBinding

class ProductSellerReviewFragment : Fragment() {

    lateinit var fragmentProductSellerReviewBinding: FragmentProductSellerReviewBinding
    lateinit var mainActivity: MainActivity

    companion object {
        fun newInstance() = ProductSellerReviewFragment()
    }

    private lateinit var viewModel: ProductSellerReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductSellerReviewBinding = FragmentProductSellerReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductSellerReviewBinding.run {

            recyclerViewProductSellerReview.run {
                adapter = RecyclerAdapterClass()

                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
        return fragmentProductSellerReviewBinding.root
    }

    override fun onResume() {
        super.onResume()
        var adapter = fragmentProductSellerReviewBinding.recyclerViewProductSellerReview.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductSellerReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowProductSellerReviewBinding: RowProductSellerReviewBinding) : RecyclerView.ViewHolder(rowProductSellerReviewBinding.root) {

            var imageViewRow : ImageView
            var textViewRowReviewTitle : TextView
            var ratingBarRow : RatingBar
            var textViewRowReviewContent : TextView

            init {
                imageViewRow = rowProductSellerReviewBinding.imageViewProductSellerReview
                textViewRowReviewTitle = rowProductSellerReviewBinding.textViewProductSellerReviewReviewTitle
                ratingBarRow = rowProductSellerReviewBinding.ratingBarProductSellerReview
                textViewRowReviewContent = rowProductSellerReviewBinding.textViewProductSellerReviewReviewContent
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowProductSellerReviewBinding.inflate(layoutInflater)
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
            return 2
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        }
    }

}