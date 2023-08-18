package com.test.keepgardeningproject_seller.UI.HomeSeller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.MainActivity.Companion.PRODUCT_SELLER_MAIN_FRAGMENT
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentHomeSellerProductBinding
import com.test.keepgardeningproject_seller.databinding.RowHomeSellerBinding

class HomeSellerProductFragment : Fragment() {

    lateinit var fragmentHomeSellerProductBinding: FragmentHomeSellerProductBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeSellerProductBinding = FragmentHomeSellerProductBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentHomeSellerProductBinding.run {
            recyclerViewHomeSellerProduct.run {
                adapter = RecyclerAdapterClass()

                layoutManager = GridLayoutManager(mainActivity,3)
            }
        }


        return fragmentHomeSellerProductBinding.root
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowHomeSellerBinding: RowHomeSellerBinding) : RecyclerView.ViewHolder(rowHomeSellerBinding.root) {
            var imageViewRow : ImageView
            var textViewRowProductName : TextView
            var textViewRowStoreName : TextView
            var textViewRowPrice : TextView

            init {
                imageViewRow = rowHomeSellerBinding.imageViewRowHomeSeller
                textViewRowProductName = rowHomeSellerBinding.textViewRowHomeSellerProductName
                textViewRowStoreName = rowHomeSellerBinding.textViewRowHomeSellerStoreName
                textViewRowPrice = rowHomeSellerBinding.textViewRowHomeSellerProductPrice

                rowHomeSellerBinding.root.setOnClickListener {
                    mainActivity.replaceFragment(PRODUCT_SELLER_MAIN_FRAGMENT,true,null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowHomeSellerBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return 9
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        }
    }
}