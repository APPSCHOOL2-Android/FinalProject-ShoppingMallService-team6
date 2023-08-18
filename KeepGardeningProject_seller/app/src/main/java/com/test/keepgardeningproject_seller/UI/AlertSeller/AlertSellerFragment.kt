package com.test.keepgardeningproject_seller.UI.AlertSeller

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
import com.test.keepgardeningproject_seller.databinding.FragmentAlertSellerBinding
import com.test.keepgardeningproject_seller.databinding.RowAlertSellerItemBinding

class AlertSellerFragment : Fragment() {
    lateinit var fragmentAlertSellerBinding: FragmentAlertSellerBinding
    lateinit var mainActivity: MainActivity
    lateinit var alertSellerViewModel: AlertSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAlertSellerBinding = FragmentAlertSellerBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentAlertSellerBinding.run {
            toolbarAlertSeller.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ALERT_SELLER_FRAGMENT)
                }
                setTitle("알림")
            }
            alertSellerViewModel = ViewModelProvider(mainActivity)[AlertSellerViewModel::class.java]
            alertSellerViewModel.run{
                alertDataList.observe(mainActivity){
                    fragmentAlertSellerBinding.recyclerviewAlertSeller.adapter?.notifyDataSetChanged()

                }
            }
            recyclerviewAlertSeller.run{
                adapter = AlertRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                //addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

        }



        return fragmentAlertSellerBinding.root

    }

    inner class AlertRecyclerViewAdapter: RecyclerView.Adapter<AlertRecyclerViewAdapter.AlertViewHolder>(){
        inner class AlertViewHolder(rowAlertBinding: RowAlertSellerItemBinding) : RecyclerView.ViewHolder(rowAlertBinding.root){
            var rowAlertCustomerItem: TextView
            init {
                rowAlertCustomerItem = rowAlertBinding.textViewRowAlertSellerItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
            var rowAlertBinding = RowAlertSellerItemBinding.inflate(layoutInflater)
            var allViewHolder = AlertViewHolder(rowAlertBinding)
            rowAlertBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {

        }
    }



}