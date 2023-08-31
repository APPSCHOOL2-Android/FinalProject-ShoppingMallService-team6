package com.test.keepgardeningproject_customer.UI.HomeCustomerSearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.DAO.ProductClass
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.databinding.FragmentHomeCustomerSearchBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsGridBinding
import com.test.keepgardeningproject_customer.databinding.RowHcsLinearBinding
import java.text.DecimalFormat

class HomeCustomerSearchFragment : Fragment() {

    lateinit var fragmentHomeCustomerSearchBinding: FragmentHomeCustomerSearchBinding
    lateinit var mainActivity: MainActivity

    val categoryList = arrayOf(
        "전체보기", "관엽식물", "다육식물", "동서양란", "분재", "농산물", "씨앗/묘목", "꽃다발", "원예자재류", "천원샵", "검색결과"
    )
    // 뷰모델
    lateinit var viewModel: HomeCustomerSearchViewModel

    // 불어와서 저장할 전체 리스트
    var productList = mutableListOf<ProductClass>()
    var productThumbnailFileNameList = mutableListOf<String>()

    // 지금 리싸이클러뷰에 보여줄 리스트
    var rpl = mutableListOf<ProductClass>()
    var ril = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeCustomerSearchBinding = FragmentHomeCustomerSearchBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(mainActivity)[HomeCustomerSearchViewModel::class.java]
        viewModel.run{
            this.getProductInfoAll()

            this.productClassList.observe(mainActivity){
                productList = it
            }

            this.productImageNameList.observe(mainActivity){
                productThumbnailFileNameList = it
            }

        }

        fragmentHomeCustomerSearchBinding.run{
            // 툴바
            toolbarHcs.run{
                title = "검색하기"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.HOME_CUSTOMER_SEARCH_FRAGMENT)
                }
            }

            // 검색창
            searchViewHcs.run{
                queryHint = "검색어를 입력해주세요"
                setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        //검색어 입력 순간마다의 이벤트...
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        //키보드에서 검색 버튼을 누르는 순간의 이벤트
                        Log.d("^^^^^^^^^^^^^^^^^^^",query.toString())
                        val resultList = mutableListOf<ProductClass>()
                        val resultImageFileNameList = mutableListOf<String>()
                        for((index,product) in productList.withIndex()){
                            if(product.productName?.contains(query) == true){
                                resultList.add(product)
                                resultImageFileNameList.add(product.productImageList?.get(0)!!)
                            }
                        }
                        rpl = resultList
                        ril = resultImageFileNameList
                        fragmentHomeCustomerSearchBinding.recyclerHcsSearchResult.adapter?.notifyDataSetChanged()
                        spinner.setSelection(9)

                        return true
                    }
                })

            }
            mainActivity.showSoftInput(searchViewHcs)

            spinner.run{
                val a1 = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryList)
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = a1

                // 번들로 받아와서 지정
                var categoryString = arguments?.getString("category").toString()
                Log.d("!!!!!!!!!!!!!!",categoryString)
                when(categoryString){
                    "전체보기"->{
                        rpl = productList
                        ril = productThumbnailFileNameList
                        fragmentHomeCustomerSearchBinding.recyclerHcsSearchResult.adapter?.notifyDataSetChanged()
                        setSelection(0)
                    }
                    "관엽식물"->{
                        getListByCategory("관엽식물")
                        setSelection(1)
                    }
                    "다육식물"->{
                        getListByCategory("다육식물")
                        setSelection(2)
                    }
                    "동서양란"->{
                        getListByCategory("동서양란")
                        setSelection(3)
                    }
                    "분재"->{
                        getListByCategory("분재")
                        setSelection(4)
                    }
                    "농산물"->{
                        getListByCategory("농산물")
                        setSelection(5)
                    }
                    "씨앗/묘목"->{
                        getListByCategory("씨앗/묘목")
                        setSelection(6)
                    }
                    "꽃다발"->{
                        getListByCategory("꽃다발")
                        setSelection(7)
                    }
                    "원예자재류"->{
                        getListByCategory("원예자재류")
                        setSelection(8)
                    }
                    "천원샵"->{
                        getListBy1000()
                        setSelection(9)
                    }
                }


                // 스피너 선택시
                onItemSelectedListener = object :OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        when(position){
                            // 전체상품
                            0->{
                                rpl = productList
                                ril = productThumbnailFileNameList
                                fragmentHomeCustomerSearchBinding.recyclerHcsSearchResult.adapter?.notifyDataSetChanged()
                            }
                            // 카테고리
                            1-> getListByCategory("관엽식물")
                            2-> getListByCategory("다육식물")
                            3-> getListByCategory("동서양란")
                            4-> getListByCategory("분재")
                            5-> getListByCategory("농산물")
                            6-> getListByCategory("씨앗/묘목")
                            7-> getListByCategory("꽃다발")
                            8-> getListByCategory("원예자재류")

                            // 천원샵
                            9 -> getListBy1000()

                            // 검색결과
                            10-> {}
                        }
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
    //
    fun getListByCategory(category : String){
        val tempList = mutableListOf<ProductClass>()
        val tempImageList = mutableListOf<String>()
        for((index,product) in productList.withIndex()){
            if(product.productCategory == category){
                tempList.add(product)
                tempImageList.add(product.productImageList?.get(0)!!)
            }
        }
        rpl = tempList
        ril = tempImageList
        fragmentHomeCustomerSearchBinding.recyclerHcsSearchResult.adapter?.notifyDataSetChanged()
    }

    fun getListBy1000(){
        val tempList = mutableListOf<ProductClass>()
        val tempImageList = mutableListOf<String>()
        for((index,product) in productList.withIndex()){
            if(product.productPrice?.toInt()!! <= 1000){
                tempList.add(product)
                tempImageList.add(product.productImageList?.get(0)!!)
            }
        }
        rpl = tempList
        ril = tempImageList
        fragmentHomeCustomerSearchBinding.recyclerHcsSearchResult.adapter?.notifyDataSetChanged()
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
                    val selectedProductIdx = rpl[adapterPosition].productIdx!!
                    val bundle = Bundle()
                    bundle.putLong("selectedProductIdx",selectedProductIdx)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT,true,bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCSGrid {
            val rowHcsGridBinding = RowHcsGridBinding.inflate(layoutInflater)
            val viewHolderHCSGrid = ViewHolderHCSGrid(rowHcsGridBinding)

            return viewHolderHCSGrid
        }

        override fun getItemCount(): Int {
            return rpl.size
        }

        override fun onBindViewHolder(holder: ViewHolderHCSGrid, position: Int) {
            // 상품 이미지
            var fileName = ril[position]
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageViewHcsGrid)
            }

            // 상품명
            holder.textViewHcsGridTitle.text = rpl[position].productName

            // 상품 가격
            var decimal = DecimalFormat("#,###")
            var temp = rpl[position].productPrice!!.toInt()
            holder.textViewHcsGridPrice.text = decimal.format(temp) + " 원"

            // 카테고리 이름
            holder.textViewHcsGridStore.text = rpl[position].productCategory

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
                    val selectedProductIdx = rpl[adapterPosition].productIdx!!
                    val bundle = Bundle()
                    bundle.putLong("selectedProductIdx",selectedProductIdx)
                    mainActivity.replaceFragment(MainActivity.PRODUCT_CUSTOMER_DETAIL_FRAGMENT,true,bundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHCSLinear {
            val rowHcsBindingLinear = RowHcsLinearBinding.inflate(layoutInflater)
            val viewHolderHCSLinear = ViewHolderHCSLinear(rowHcsBindingLinear)

            return viewHolderHCSLinear
        }

        override fun getItemCount(): Int {
            return rpl.size
        }

        override fun onBindViewHolder(holder: ViewHolderHCSLinear, position: Int) {
            // 상품 이미지
            var fileName = ril[position]
            ProductRepository.getProductImage(fileName){
                var fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageHcsLinear)
            }

            // 상품명
            holder.textViewHcsLinearTitle.text = rpl[position].productName

            // 상품 가격
            var decimal = DecimalFormat("#,###")
            var temp = rpl[position].productPrice!!.toInt()
            holder.textViewHcsLinearPrice.text = decimal.format(temp) + " 원"

            // 스토어 이름
            holder.textViewHcsLinearStore.text = rpl[position].productCategory
        }
    }
}