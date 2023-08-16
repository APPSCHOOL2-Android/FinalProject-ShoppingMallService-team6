package com.test.keepgardeningproject_seller.UI.AlterSeller

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.keepgardeningproject_seller.R

class AlterSellerFragment : Fragment() {

    companion object {
        fun newInstance() = AlterSellerFragment()
    }

    private lateinit var viewModel: AlterSellerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alter_seller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlterSellerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}