package com.test.keepgardeningproject_seller.UI.HomeSeller

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class HomeSellerFragment : Fragment() {

    companion object {
        fun newInstance() = HomeSellerFragment()
    }

    private lateinit var viewModel: HomeSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_seller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeSellerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}