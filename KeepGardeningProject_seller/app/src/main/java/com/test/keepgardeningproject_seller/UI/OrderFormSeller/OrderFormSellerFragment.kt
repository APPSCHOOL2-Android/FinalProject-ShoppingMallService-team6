package com.test.keepgardeningproject_seller.UI.OrderFormSeller

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class OrderFormSellerFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFormSellerFragment()
    }

    private lateinit var viewModel: OrderFormSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_form_seller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderFormSellerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}