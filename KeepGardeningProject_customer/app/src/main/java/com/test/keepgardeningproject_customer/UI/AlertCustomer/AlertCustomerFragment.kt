package com.test.keepgardeningproject_customer.UI.AlertCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentAlertCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowAlertCustomerItemBinding

class AlertCustomerFragment : Fragment() {
    lateinit var fragmentAlertCustomerBinding: FragmentAlertCustomerBinding
    lateinit var mainActivity: MainActivity
    lateinit var alertCustomerViewModel: AlertCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAlertCustomerBinding = FragmentAlertCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentAlertCustomerBinding.run {
            toolbarAlertCustomer.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ALERT_CUSTOMER_FRAGMENT)
                }
                setTitle("알림")
            }
            alertCustomerViewModel = ViewModelProvider(mainActivity)[AlertCustomerViewModel::class.java]
            alertCustomerViewModel.run{
                alertDataList.observe(mainActivity){
                    fragmentAlertCustomerBinding.recyclerviewAlertCustomer.adapter?.notifyDataSetChanged()

                }
            }
            recyclerviewAlertCustomer.run{
                adapter = AlertRecyclerViewAdater()
                layoutManager = LinearLayoutManager(context)
                //addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

        }



        return fragmentAlertCustomerBinding.root

    }

    inner class AlertRecyclerViewAdater: RecyclerView.Adapter<AlertRecyclerViewAdater.AlertViewHolder>(){
        inner class AlertViewHolder(rowAlertBinding: RowAlertCustomerItemBinding) : RecyclerView.ViewHolder(rowAlertBinding.root){
            var rowAlertCustomerItem: TextView
            init {
                rowAlertCustomerItem = rowAlertBinding.textViewRowAlertCustomerItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
            var rowAlertBinding = RowAlertCustomerItemBinding.inflate(layoutInflater)
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