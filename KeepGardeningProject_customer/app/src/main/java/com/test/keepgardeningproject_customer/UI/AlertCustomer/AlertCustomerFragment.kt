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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentAlertCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowAlterCustomerItemBinding

class AlertCustomerFragment : Fragment() {
    lateinit var fragmentAlterCustomerBinding: FragmentAlertCustomerBinding
    lateinit var mainActivity: MainActivity
    lateinit var alterCustomerViewModel: AlertCustomerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAlterCustomerBinding = FragmentAlertCustomerBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        fragmentAlterCustomerBinding.run {
            toolbarAlterCustomer.run {
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ALERT_CUSTOMER_FRAGMENT)
                }
                setTitle("알림")
            }
            alterCustomerViewModel = ViewModelProvider(mainActivity)[AlertCustomerViewModel::class.java]
            alterCustomerViewModel.run{
                alterDataList.observe(mainActivity){
                    fragmentAlterCustomerBinding.recyclerviewAlterCustomer.adapter?.notifyDataSetChanged()

                }
            }
            recyclerviewAlterCustomer.run{
                adapter = AlterRecyclerViewAdater()
                layoutManager = LinearLayoutManager(context)
                //addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

        }



        return fragmentAlterCustomerBinding.root

    }

    inner class AlterRecyclerViewAdater: RecyclerView.Adapter<AlterRecyclerViewAdater.AlterViewHolder>(){
        inner class AlterViewHolder(rowAlterBinding: RowAlterCustomerItemBinding) : RecyclerView.ViewHolder(rowAlterBinding.root){
            var rowAlterCustomerItem: TextView
            init {
                rowAlterCustomerItem = rowAlterBinding.textViewRowAlterCustomerItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlterViewHolder {
            var rowAlterBinding = RowAlterCustomerItemBinding.inflate(layoutInflater)
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