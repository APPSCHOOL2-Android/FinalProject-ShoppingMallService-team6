package com.test.keepgardeningproject_customer.UI.HomeCustomerSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerSearchBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsGridBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsLinearBinding
import org.w3c.dom.Text

class HomeCustomerSearchFragment : Fragment() {

    lateinit var fragmentHomeCustomerSearchBinding: FragmentHomeCustomerSearchBinding
    lateinit var mainActivity: MainActivity

    val categoryList = arrayOf(
        "관엽식물", "다육식물", "동서양란", "분재", "농산물", "씨앗/묘목", "꽃다발", "원예자재류"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeCustomerSearchBinding = FragmentHomeCustomerSearchBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentHomeCustomerSearchBinding.run{
            toolbarHcs.run{
                title = "검색하기"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT)
                }
            }

            searchbarHcs.run{
                title = "검색어를 입력해 주세요"
                requestFocus()
            }
            mainActivity.showSoftInput(searchbarHcs)

            spinner.run{
                val a1 = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryList)
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = a1
                setSelection(0)

                // 스피너 선택시
                onItemSelectedListener = object :OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                }
            }

            recyclerHcsSearchResult.run{
                adapter = RecyclerAdapterHCSGrid()
                layoutManager = GridLayoutManager(context, 2)
            }

            // 버튼 클릭시 레이아웃 매니저 변경
            imageHcsShowGrid.setOnClickListener {
                recyclerHcsSearchResult.adapter = RecyclerAdapterHCSGrid()
                recyclerHcsSearchResult.layoutManager = GridLayoutManager(context,2)
            }

            imageViewHcsShowLinear.setOnClickListener {
                recyclerHcsSearchResult.adapter = RecyclerAdapterHCSLinear()
                recyclerHcsSearchResult.layoutManager = LinearLayoutManager(context)
            }
        }

        return fragmentHomeCustomerSearchBinding.root
    }

    // 그리드 리싸이클러 어댑터
    inner class RecyclerAdapterHCSGrid : RecyclerView.Adapter<RecyclerAdapterHCSGrid.ViewHolderHCSGrid>(){
        inner class ViewHolderHCSGrid(rowHcsGridBinding: RowHcsGridBinding) : RecyclerView.ViewHolder(rowHcsGridBinding.root){
            val imageViewHcsGrid : ImageView
            val textViewHcsGridTitle : TextView
            val textViewHcsGridPrice : TextView
            val textViewHcsGridStore : TextView

            init {
                imageViewHcsGrid = rowHcsGridBinding.imageViewHcsGrid
                textViewHcsGridTitle = rowHcsGridBinding.textViewHcsGridTitle
                textViewHcsGridPrice = rowHcsGridBinding.textViewHcsGridPrice
                textViewHcsGridStore = rowHcsGridBinding.textViewHcsGridStore

                // 클릭시 개별 아이템 상세페이지 이동
                rowHcsGridBinding.root.setOnClickListener {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCSGrid {
            val rowHcsGridBinding = RowHcsGridBinding.inflate(layoutInflater)
            val viewHolderHCSGrid = ViewHolderHCSGrid(rowHcsGridBinding)

//            rowHcsBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            return viewHolderHCSGrid
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: ViewHolderHCSGrid, position: Int) {
            holder.textViewHcsGridTitle.text = "상품명"
            holder.textViewHcsGridPrice.text = "10,000원"
            holder.textViewHcsGridStore.text = "채윤스토어"
        }
    }

    // 리니어 리싸이클러 어댑터
    inner class RecyclerAdapterHCSLinear: RecyclerView.Adapter<RecyclerAdapterHCSLinear.ViewHolderHCSLinear>(){
        inner class ViewHolderHCSLinear(rowHcsLinearBinding: RowHcsLinearBinding) : RecyclerView.ViewHolder(rowHcsLinearBinding.root){
            val imageHcsLinear : ImageView
            val textViewHcsLinearTitle : TextView
            val textViewHcsLinearPrice : TextView
            val textViewHcsLinearStore : TextView

            init {
                imageHcsLinear = rowHcsLinearBinding.imageHcsLinear
                textViewHcsLinearTitle = rowHcsLinearBinding.textViewHcsLinearTitle
                textViewHcsLinearStore = rowHcsLinearBinding.textViewHcsLinearStore
                textViewHcsLinearPrice = rowHcsLinearBinding.textViewHcsLinearPrice

                // 클릭시 개별 아이템 상세페이지 이동
                rowHcsLinearBinding.root.setOnClickListener {

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCSLinear {
            val rowHcsBindingLinear = RowHcsLinearBinding.inflate(layoutInflater)
            val viewHolderHCSLinear = ViewHolderHCSLinear(rowHcsBindingLinear)

//            rowHcsBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )

            return viewHolderHCSLinear
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: ViewHolderHCSLinear, position: Int) {
            holder.textViewHcsLinearTitle.text = "상품명"
            holder.textViewHcsLinearStore.text = "채윤스토어"
            holder.textViewHcsLinearPrice.text = "10,000원"
        }
    }
}