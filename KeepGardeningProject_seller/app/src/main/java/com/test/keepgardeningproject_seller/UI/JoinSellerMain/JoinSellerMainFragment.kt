package com.test.keepgardeningproject_seller.UI.JoinSellerMain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class JoinSellerMainFragment : Fragment() {

    companion object {
        fun newInstance() = JoinSellerMainFragment()
    }

    private lateinit var viewModel: JoinSellerMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_seller_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JoinSellerMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}