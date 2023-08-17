package com.test.keepgardeningproject_seller.UI.AlterSeller

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.R
import com.test.keepgardeningproject_seller.databinding.FragmentAlterSellerBinding
import com.test.keepgardeningproject_seller.databinding.RowAlterSellerItemBinding

class AlterSellerFragment : Fragment() {
    lateinit var fragmentAlterSellerBinding: FragmentAlterSellerBinding
    lateinit var mainActivity: MainActivity
    lateinit var alterSellerViewModel: AlterSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAlterSellerBinding = FragmentAlterSellerBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentAlterSellerBinding.run {
            toolbarAlterSeller.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ALTER_SELLER_FRAGMENT)
                }
                setTitle("알림")
            }
            alterSellerViewModel = ViewModelProvider(mainActivity)[AlterSellerViewModel::class.java]
            alterSellerViewModel.run{
                alterDataList.observe(mainActivity){
                    fragmentAlterSellerBinding.recyclerviewAlterSeller.adapter?.notifyDataSetChanged()

                }
            }
            recyclerviewAlterSeller.run{
                adapter = AlterRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                //addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

        }



        return fragmentAlterSellerBinding.root

    }

    inner class AlterRecyclerViewAdapter: RecyclerView.Adapter<AlterRecyclerViewAdapter.AlterViewHolder>(){
        inner class AlterViewHolder(rowAlterBinding: RowAlterSellerItemBinding) : RecyclerView.ViewHolder(rowAlterBinding.root){
            var rowAlterCustomerItem: TextView
            init {
                rowAlterCustomerItem = rowAlterBinding.textViewRowAlterSellerItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlterViewHolder {
            var rowAlterBinding = RowAlterSellerItemBinding.inflate(layoutInflater)
            var allViewHolder = AlterViewHolder(rowAlterBinding)
            rowAlterBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: AlterViewHolder, position: Int) {

        }
    }



}