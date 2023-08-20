package com.test.keepgardeningproject_customer.UI.ProductCustomerDetail.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentProductCustomerDetailDetailBinding
import com.test.keepgardeningproject_customer.databinding.RowPcddBinding


class ProductCustomerDetailDetailFragment : Fragment() {
    lateinit var fragmentProductCustomerDetailDetailBinding: FragmentProductCustomerDetailDetailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductCustomerDetailDetailBinding = FragmentProductCustomerDetailDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentProductCustomerDetailDetailBinding.run{
            recyclerPcdd.run{
                adapter = RecyclerAdapterPCDD()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentProductCustomerDetailDetailBinding.root
    }


    inner class RecyclerAdapterPCDD : RecyclerView.Adapter<RecyclerAdapterPCDD.ViewHolderPCDD>(){
        inner class ViewHolderPCDD(rowPcddBinding: RowPcddBinding) : RecyclerView.ViewHolder(rowPcddBinding.root){
            val imagePcddRow : ImageView

            init{
                imagePcddRow = rowPcddBinding.imagePcddRow
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPCDD {
            val rowPcddBinding = RowPcddBinding.inflate(layoutInflater)
            val viewHolderPCDD = ViewHolderPCDD(rowPcddBinding)

            return viewHolderPCDD
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ViewHolderPCDD, position: Int) {
            holder.imagePcddRow.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}